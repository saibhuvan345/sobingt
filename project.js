document.querySelectorAll('button').forEach(button => {
  button.addEventListener('click', (e) => {
    if (button.textContent === "Learn More") {
      document.getElementById('features').scrollIntoView({ behavior: 'smooth' });
    }
  });
});
