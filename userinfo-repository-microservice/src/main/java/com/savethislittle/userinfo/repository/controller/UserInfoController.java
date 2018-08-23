package com.savethislittle.userinfo.repository.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.savethislittle.userinfo.repository.entity.Expenses;
import com.savethislittle.userinfo.repository.entity.User;
import com.savethislittle.userinfo.repository.service.UserInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class UserInfoController {

	private UserInfoService userInfoService;

	@PostMapping("/user")
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
		// log.info("createEmployee dataIn => {}", employee);
		User userInserted = userInfoService.createUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userInserted.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/user/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {

		User user = userInfoService.findUserByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@PostMapping("/expenses")
	public void createExpense(@Valid @RequestBody Expenses expense) {
		// log.info("createEmployee dataIn => {}", employee);
		// User userInserted = userInfoService.createUser(user);
		userInfoService.createExpense(expense);
		// URI location =
		// ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userInserted.getId()).toUri();
		// /return ResponseEntity.created(location).build();
	}

	@GetMapping("/expenses/{email}")
	public ResponseEntity<List<Expenses>> findExpensesById(@PathVariable String email) {

		List<Expenses> expenses = userInfoService.searchExpenseByUser(email);
		// log.info("findById dataOut => employees: {}", employees);
		return new ResponseEntity<>(expenses, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> usersList = userInfoService.findAll();
		return new ResponseEntity<>(usersList, HttpStatus.OK);

	}

	@PutMapping("/user")
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user){
		userInfoService.updateUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/expenses/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
		// log.info("deleteEmployee dataIn => id: {}", id);
		userInfoService.deleteExpense(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/user/{email}")
	public ResponseEntity<Void>deleteUserByEmail(@PathVariable String email){
		userInfoService.deleteUserByEmail(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
