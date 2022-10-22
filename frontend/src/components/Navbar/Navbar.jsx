import React from 'react';
import { Link } from 'react-router-dom';

import './Navbar.css';

const Navbar = (isLoggedIn) => {

  return (
    <nav className="app__navbar">
        <ul className="app__navbar-links">
            <li className="app__navbar-links__elem"><Link to='/'>Home</Link></li>
            {isLoggedIn
                ?   <li className="app__navbar-links__elem"><Link to='/login'>Login</Link></li>
                :   <li className="app__navbar-links__elem"><Link to='/profile'>Profile</Link></li>
            }
        </ul>
    </nav>
  )
}

export default Navbar;