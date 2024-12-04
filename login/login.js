document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById('loginForm');
    const accountNumberInput = document.getElementById('accountNumber');
    const passwordInput = document.getElementById('password');
  
    loginForm.addEventListener("submit", function(event) {
      // Prevent form submission if validation fails
      event.preventDefault();
  
      const accountNumber = accountNumberInput.value;
      const password = passwordInput.value;
  
      // Check if fields are not empty
      if (accountNumber === "" || password === "") {
        alert("Please fill in all fields.");
      } else {
        // Proceed with form submission (this can be customized as needed)
        alert("Login successful!");
        
      }
    });
  });
  