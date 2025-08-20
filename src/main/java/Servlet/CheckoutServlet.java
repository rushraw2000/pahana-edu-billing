package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            String accountNumber = request.getParameter("account");
            Customer customer = customerDAO.getCustomer(accountNumber);
            request.setAttribute("customer", customer);
            request.getRequestDispatcher("/WEB-INF/views/customer-form.jsp").forward(request, response);
        } else {
            List<Customer> customers = customerDAO.getAllCustomers();
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/WEB-INF/views/customer-management.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            Customer customer = new Customer(
                    request.getParameter("accountNumber"),
                    request.getParameter("customerName"),
                    request.getParameter("address"),
                    request.getParameter("telephone"),
                    request.getParameter("email")
            );

            if (customerDAO.addCustomer(customer)) {
                request.setAttribute("success", "Customer added successfully");
            } else {
                request.setAttribute("error", "Failed to add customer");
            }
        } else if ("update".equals(action)) {
            Customer customer = new Customer(
                    request.getParameter("accountNumber"),
                    request.getParameter("customerName"),
                    request.getParameter("address"),
                    request.getParameter("telephone"),
                    request.getParameter("email")
            );

            if (customerDAO.updateCustomer(customer)) {
                request.setAttribute("success", "Customer updated successfully");
            } else {
                request.setAttribute("error", "Failed to update customer");
            }
        } else if ("delete".equals(action)) {
            String accountNumber = request.getParameter("accountNumber");

            if (customerDAO.deleteCustomer(accountNumber)) {
                request.setAttribute("success", "Customer deleted successfully");
            } else {
                request.setAttribute("error", "Failed to delete customer");
            }
        }

        response.sendRedirect("customers");
    }
}