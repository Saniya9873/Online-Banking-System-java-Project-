document.getElementById('loginForm').addEventListener('submit', function(event) {
  event.preventDefault(); // Prevent form submission
  const accountNumber = document.getElementById('accountNumber').value;
  const password = document.getElementById('password').value;

  if (accountNumber && password) {
    alert('Login successful!');
  } else {
    alert('Please fill in all fields.');
  }
});
