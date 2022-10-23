import React, {useState, useEffect} from 'react'
import { Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import Profile from './components/Profile/Profile';
import Forum from './pages/Forum/Forum';
import Login from './pages/Login/Login';

const App = () => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const parentHandleChange = (e) => {
        setIsLoggedIn(e)
      };

      useEffect(() => {
        if(sessionStorage.getItem("access-token")){
            setIsLoggedIn(true);
        }
      }, [])

  return (
    <div>
        <Navbar isLoggedIn={isLoggedIn} handleChangeProp={parentHandleChange}/>
        {
            isLoggedIn === true &&
            <Profile handleChangeProp={parentHandleChange}/>
        }
        <Routes>
            <Route path="/" element={<Forum />}/>
            <Route path="/login" element=
            {
                isLoggedIn === false &&
                <Login handleChangeProp={parentHandleChange}/>
            }/>
        </Routes>

    </div>
  )
}

export default App