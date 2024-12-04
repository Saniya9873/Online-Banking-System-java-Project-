document.addEventListener("DOMContentLoaded", function () {
    // Example JavaScript: Dynamic change of text in the header
    const headerText = document.querySelector('.text-box h1');
    setTimeout(function () {
        headerText.innerHTML = "Welcome to XYZ Bank!";
    }, 3000);  // Changes header text after 3 seconds
});
;
  