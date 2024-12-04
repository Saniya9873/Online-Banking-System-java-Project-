document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".form");
  
    form.addEventListener("submit", (event) => {
      event.preventDefault();
  
      // Custom Bootstrap form validation
      if (!form.checkValidity()) {
        event.stopPropagation();
        alert("Please fill in all required fields.");
        return;
      }
  
      const fullName = document.querySelector("input[placeholder='Enter full name']").value.trim();
      const fatherName = document.querySelector("input[placeholder='Enter name']").value.trim();
      const phoneNumber = document.querySelector("input[placeholder='Enter phone number']").value.trim();
  
      if (phoneNumber.length !== 10) {
        alert("Phone number must be exactly 10 digits.");
        return;
      }
  
      alert("Form submitted successfully!");
    });
  });
  