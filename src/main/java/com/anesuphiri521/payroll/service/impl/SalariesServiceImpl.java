package com.anesuphiri521.payroll.service.impl;

import com.anesuphiri521.payroll.model.Salary;
import com.anesuphiri521.payroll.service.SalariesService;
import com.anesuphiri521.payroll.utils.Constants;
import com.anesuphiri521.payroll.utils.Enums.Currency;
import com.anesuphiri521.payroll.utils.Enums.PaymentState;
import com.anesuphiri521.payroll.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SalariesServiceImpl implements SalariesService {

    private final SalaryRepository salaryRepository;
    @Override
    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    @Override
    public List<Salary> getPendingSalaries() {
        return salaryRepository.findByPaymentState(PaymentState.PENDING);
    }

    @Override
    public Double todayTotalValueUSD() {
        return salaryRepository.findTodayTotalValueUSD();
    }

    @Override
    public Double todayTotalValueZWL() {
        return salaryRepository.findTodayTotalValueZWL();
    }

    @Override
    public Integer todayTotal() {
        return salaryRepository.todayTotal();
    }

    @Override
    public Salary saveSalary(Salary salary) {
        salary.setPaymentState(PaymentState.PENDING);
        return salaryRepository.save(salary);
    }

    @Override
    public String saveBatchUploadExcel(String fileName) throws IOException {
        String narration = "";
        // create object of input stream
        FileInputStream fileInputStream = new FileInputStream(Constants.uploadDir +"/" + fileName);

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

        Row row;
        List<Salary> salaryList = new ArrayList<Salary>();
        for (int j = 1; j <= xssfSheet.getLastRowNum(); j++) {
            row = xssfSheet.getRow(j);
            Salary salary = new Salary();
            salary.setReceiverName(row.getCell(0).toString());
            salary.setReceiverAccountNumber(String.valueOf((int)row.getCell(1).getNumericCellValue()));

            if(row.getCell(2).getStringCellValue().equalsIgnoreCase("USD"))
                salary.setCurrency(Currency.USD);

            if(row.getCell(2).getStringCellValue().equalsIgnoreCase("ZWL"))
                salary.setCurrency(Currency.ZWL);

            salary.setAmount(row.getCell(3).getNumericCellValue());
            salary.setSenderName(row.getCell(4).getStringCellValue());
            salary.setSenderAccountNumber(String.valueOf((int)row.getCell(5).getNumericCellValue()));
            salary.setPaymentState(PaymentState.PENDING);

            log.info(row.getCell(0) + " " + " " + row.getCell(1) + " " + row.getCell(2) + " " + row.getCell(3) + " " + row.getCell(4) +" " + row.getCell(5));

            salaryList.add(salary);
        }
        xssfWorkbook.close();

        salaryRepository.saveAll(salaryList);


        return narration;
    }
    public String saveBatchUploadCSV(String fileName) throws IOException {
        String narration = "";
        String extension = FilenameUtils.getExtension(Constants.uploadDir +"/" + fileName);
        List<Salary> salaryList = new ArrayList<Salary>();

        if (extension.equalsIgnoreCase("csv")){
            try (
                    Reader reader = Files.newBufferedReader(Paths.get(Constants.uploadDir +"/" + fileName));
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            ) {
                for (CSVRecord csvRecord : csvParser) {
                    Salary salary = new Salary();
                    salary.setReceiverName(csvRecord.get(0));
                    salary.setReceiverAccountNumber(csvRecord.get(1));

                    if (csvRecord.get(2).equalsIgnoreCase("USD"))
                        salary.setCurrency(Currency.USD);

                    if (csvRecord.get(2).equalsIgnoreCase("ZWL"))
                        salary.setCurrency(Currency.ZWL);

                    salary.setAmount(Double.valueOf(csvRecord.get(3)));
                    salary.setSenderName(csvRecord.get(4));
                    salary.setSenderAccountNumber(csvRecord.get(5));
                    salary.setPaymentState(PaymentState.PENDING);
                    salaryList.add(salary);
                }
            }

            salaryRepository.saveAll(salaryList);
            narration = "File uploaded successfully, records saved";
        }else {
            narration ="File is not CSV";
        }
        return narration;
    }

    @Override
    public Optional<Salary> findPaymentById(Long id) {
        return salaryRepository.findById(id);
    }

    @Override
    public void updateSalaryApprove(Long id) {
        Optional<Salary> getSalary = salaryRepository.findById(id);
        if (getSalary.isPresent()){
            log.info("Updating salary");
            getSalary.get().setPaymentState(PaymentState.APPROVED);
            salaryRepository.save(getSalary.get());
        }
    }

    @Override
    public void updateSalaryReject(Long id) {
        Optional<Salary> getSalary = salaryRepository.findById(id);
        if (getSalary.isPresent()){
            log.info("Updating salary");
            getSalary.get().setPaymentState(PaymentState.REJECTED);
            salaryRepository.save(getSalary.get());
        }
    }


}
