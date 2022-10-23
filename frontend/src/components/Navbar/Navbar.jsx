import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';

import './Navbar.css';

const Navbar = (props) => {

    const [value, setValue] = useState(props.isLoggedIn);

      useEffect(() => {
        setValue(props.isLoggedIn)
      }, [props])

    const logout = () => {
        sessionStorage.clear();
        props.handleChangeProp(false);
      };
    

  return (
    <nav className="app__navbar">
        <ul className="app__navbar-links">
            <li className="app__navbar-links__elem"><Link to='/'>Home</Link></li>
            {value === true
                ?   <li className="app__navbar-links__elem" onClick={logout}><Link to='/'>Logout</Link></li>
                :   <li className="app__navbar-links__elem"><Link to='/login'>Login</Link></li>
            }
        </ul>
    </nav>
  )
}

export default Navbar;