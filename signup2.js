document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById("signupForm");
  
    signupForm.addEventListener("submit", function (event) {
      event.preventDefault();
  
      const panNumber = document.getElementById("panNumber").value;
      const aadharNumber = document.getElementById("aadharNumber").value;
      
      if (!panNumber || !aadharNumber) {
        alert("Please fill in all required fields.");
      } else {
        alert("Form submitted successfully!");
        // If needed, you can send the form data to the server here.
      }
    });
  
    // Handling the "Back" button
    const backButton = document.querySelector(".btn-secondary");
    backButton.addEventListener("click", function() {
      window.history.back(); // Go back to the previous page
    });
  });
  