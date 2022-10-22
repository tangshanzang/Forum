import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';

import './Navbar.css';

const Navbar = (props) => {

    const [value, setValue] = useState(props.isLoggedIn);

      useEffect(() => {
        setValue(props.isLoggedIn)
      }, [props])
    

  return (
    <nav className="app__navbar">
        <ul className="app__navbar-links">
            <li className="app__navbar-links__elem"><Link to='/'>Home</Link></li>
            {value == true
                ?   <li className="app__navbar-links__elem"><Link to='/profile'>Profile</Link></li>
                :   <li className="app__navbar-links__elem"><Link to='/login'>Login</Link></li>
            }
        </ul>
    </nav>
  )
}

export default Navbar;