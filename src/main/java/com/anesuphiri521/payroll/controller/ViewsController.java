package com.anesuphiri521.payroll.controller;

import com.anesuphiri521.payroll.dto.NewUserDto;
import com.anesuphiri521.payroll.dto.NewUserResponseDto;
import com.anesuphiri521.payroll.model.Salary;
import com.anesuphiri521.payroll.utils.Constants;
import com.anesuphiri521.payroll.service.SalariesService;
import com.anesuphiri521.payroll.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
@AllArgsConstructor
@Slf4j
public class ViewsController {
    private SalariesService salariesService;
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/")
    public String userLogin(Model model){
        return "login";
    }


    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("salaries", salariesService.getPendingSalaries());
        return "home";
    }

    @GetMapping("/salaries")
    public String salaries(Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("salaries", salariesService.getAllSalaries());
        return "salaries";
    }

    @GetMapping("/add-salary")
    public String showForms(Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("salaryForm", new Salary());
        return "add-salary";
    }

    @PostMapping("/new-salary")
    public String addSalaries(@ModelAttribute Salary salary, Model model){
        model.addAttribute("salaryForm", new Salary());
        model.addAttribute("role", userPermission());
        model.addAttribute("narration", "Salary added successfully");
        salariesService.saveSalary(salary);
        return "add-salary";
    }

    @PostMapping("/new-salary-excel")
    public String addSalariesExcel(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        model.addAttribute("salaryForm", new Salary());
        if (file.isEmpty()) {
            model.addAttribute("narrationFile", "Please select a file to upload.");
            return "add-salary";
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Path path = Paths.get(Constants.uploadDir, fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String narration = salariesService.saveBatchUploadExcel(fileName);
        model.addAttribute("narrationFile", narration);
        return "add-salary";
    }

    @PostMapping("/new-salary-csv")
    public String addSalariesCSV(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        model.addAttribute("salaryForm", new Salary());
        if (file.isEmpty()) {
            model.addAttribute("narrationFile", "Please select a file to upload.");
            return "add-salary";
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Path path = Paths.get(Constants.uploadDir, fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String narration = salariesService.saveBatchUploadCSV(fileName);
        model.addAttribute("narrationFile", narration);
        return "add-salary";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/add-user")
    public String showFormsUser(Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("userForm", new NewUserDto());
        return "add-user";
    }

    @PostMapping("/new-user")
    public String addUser(@ModelAttribute NewUserDto newUserDto, Model model){
        model.addAttribute("userForm", new NewUserDto());
        NewUserResponseDto responseDto = userService.saveUser(newUserDto);
        model.addAttribute("role", userPermission());
        model.addAttribute("narration", responseDto.getNarration());
        return "add-user";
    }

    @GetMapping("/reports")
    public String reports(Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("salaries", salariesService.getPendingSalaries());
        model.addAttribute("totalValueUSD", salariesService.todayTotalValueUSD());
        model.addAttribute("totalValueZWL", salariesService.todayTotalValueZWL());
        model.addAttribute("total", salariesService.todayTotal());
        return "reports";
    }

    @GetMapping("/view-salary/{id}")
    public String viewSalary(@PathVariable Long id, Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("salary", salariesService.findPaymentById(id));
        return "view-salary";
    }

    @GetMapping("/update-salary-approve/{id}")
    public String updateSalaryApprove(@PathVariable Long id, Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("salaries", salariesService.getPendingSalaries());
        model.addAttribute("salary", salariesService.findPaymentById(id));
        salariesService.updateSalaryApprove(id);
        return "home";
    }

    @GetMapping("/update-salary-reject/{id}")
    public String updateSalaryReject(@PathVariable Long id, Model model){
        model.addAttribute("role", userPermission());
        model.addAttribute("salaries", salariesService.getPendingSalaries());
        model.addAttribute("salary", salariesService.findPaymentById(id));
        salariesService.updateSalaryReject(id);
        return "home";
    }

    public String userPermission(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserRole(auth.getName());
    }
}
