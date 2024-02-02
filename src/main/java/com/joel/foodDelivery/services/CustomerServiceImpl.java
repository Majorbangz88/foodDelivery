package com.joel.foodDelivery.services;

import com.joel.foodDelivery.data.models.Customer;
import com.joel.foodDelivery.data.models.Order;
import com.joel.foodDelivery.data.repositories.CustomerRepository;
import com.joel.foodDelivery.dtos.requests.*;
import com.joel.foodDelivery.exceptions.EmailAndUsernameTakenException;
import com.joel.foodDelivery.exceptions.UserNotFoundException;
import com.joel.foodDelivery.exceptions.NullUsernameAndPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.joel.foodDelivery.java.utils.Mapper.map;

@Service
@Component
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FeedbackService feedbackService;

//    @Autowired
//    public void setUp(OrderService orderService){
//          this.orderService = orderService;
//    }

    @Override
    public Customer registerCustomer(CustomerRegistrationRequest registrationRequest) {
        validateIsNotNullEmailOrPassword(registrationRequest);
        validateEmailAndUsername(registrationRequest.getEmail(), registrationRequest.getUsername(), registrationRequest.getPhoneNumber());
        Customer customer = map(registrationRequest);
        customerRepository.save(customer);
        return customer;
    }

    private void validateIsNotNullEmailOrPassword(CustomerRegistrationRequest registrationRequest) {
        boolean isNullUsername = registrationRequest.getUsername() == null;
        boolean isNullPassword = registrationRequest.getPassword() == null;
        if (isNullUsername || isNullPassword)
            throw new UserNotFoundException("Invalid username or password");
    }

    private void validateEmailAndUsername(String email, String username, String phoneNumber) {
        Optional<Customer> customer = customerRepository.findByEmailAndUsernameAndPhoneNumber(email, username, phoneNumber);
        if (customer.isPresent())
            throw new EmailAndUsernameTakenException("This user exists");
    }

    @Override
    public Long count() {
        return customerRepository.count();
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();;
    }

    @Override
    public Customer unlock(LoginRequest loginRequest) {
        validateIsNOTNullUsernameOrPassword(loginRequest);
        Optional<Customer> customer = customerRepository.findByUsernameAndPassword(
                loginRequest.getUsername(), loginRequest.getPassword());
        if (customer.isEmpty())
            throw new UserNotFoundException("This user does not exist");
        Customer oldCustomer = customer.get();
        if (oldCustomer.getUsername().equals(loginRequest.getUsername())
                && oldCustomer.getPassword().equals(loginRequest.getPassword()))
            oldCustomer.setLocked(false);

        customerRepository.save(oldCustomer);

        return oldCustomer;
    }

    @Override
    public Customer setLock(String name) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(name);
//        optionalCustomer.ifPresent(customer -> customer.setLocked(true));
        if (optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            customer.setLocked(true);
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Override
    public PlaceOrderResponse createOrder(PlaceOrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @Override
    public Order updateOrder(UpdateOrderRequest updateOrder) {
        return orderService.updateOrder(updateOrder);
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Order cancelOrder(PlaceOrderRequest orderRequest) {
        return orderService.cancelOrder(orderRequest);
    }

    @Override
    public List<Order> findAllTransactionHistory() {
        return orderService.displayOrderHistory();
    }

    @Override
    public Optional<Customer> findCustomerBy(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public FeedbackResponse provideFeedback(FeedbackRequest feedbackRequest) {
        return feedbackService.provideFeedback(feedbackRequest);
    }

    private void validateIsNOTNullUsernameOrPassword(LoginRequest loginRequest) {
        boolean isNullUsername = loginRequest.getUsername() == null;
        boolean IsNullPassword = loginRequest.getPassword() == null;
        if (isNullUsername || IsNullPassword)
            throw new NullUsernameAndPasswordException("Invalid username or password");
    }
}
