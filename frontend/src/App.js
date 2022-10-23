import React, {useState, useEffect} from 'react'
import { Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import Forum from './pages/Forum/Forum';
import Login from './pages/Login/Login';

const App = () => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const parentHandleChange = (e) => {
        setIsLoggedIn(e)
      };

      useEffect(() => {
        console.log('isLoggedIn', isLoggedIn);
      }, [isLoggedIn])

  return (
    <div>
        <Navbar isLoggedIn={isLoggedIn} handleChangeProp={parentHandleChange}/>
        <Routes>
            <Route path="/" element={<Forum />}/>
            <Route path="/login" element={<Login handleChangeProp={parentHandleChange}/>}/>
        </Routes>

    </div>
  )
}

export default App