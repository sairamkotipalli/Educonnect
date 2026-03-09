<<<<<<< HEAD
const getVal = (id) => {
  const el = typeof document !== 'undefined' ? document.getElementById(id) : null;
  return el ? el.value : '';
};

const pickFirst = (ids) => {
  for (const id of ids) {
    const v = getVal(id);
    if (v !== '') return v;
  }
  return '';
};

function login() {
  const username = pickFirst(['loginUsername', 'username', 'login-username']);
  const password = pickFirst(['loginPassword', 'password', 'login-password']);
  console.log(`Login clicked. Username: ${username}, Password: ${password}`);
}

function register() {
  const name = pickFirst(['regName', 'name', 'fullName', 'registerName']);
  const email = pickFirst(['regEmail', 'email', 'registerEmail']);
  const username = pickFirst(['regUsername', 'username', 'registerUsername']);
  const password = pickFirst(['regPassword', 'password', 'registerPassword']);
  console.log(`Register clicked. Name: ${name}, Email: ${email}, Username: ${username}, Password: ${password}`);
}

if (typeof module !== 'undefined' && module.exports) {
  module.exports = { login, register };
}
if (typeof window !== 'undefined') {
  window.login = login;
  window.register = register;
}
=======
function login() {
   
    // You can perform login validation and authentication here
    // For simplicity, let's just display an alert
    
}

function register() {
   

    // Frontend validation for registration form
    

    // Validate email format
    
    // Validate username (no special characters)
    

    // Validate password (at least 8 characters, one capital letter, and one numeric)
    
}
module.exports = { login, register };
>>>>>>> d76e0db293a8626f50b912ae8482fe15a23e1abc
