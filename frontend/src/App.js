import React from 'react'
import { Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar/Navbar';
import Forum from './pages/Forum/Forum';
import Login from './pages/Login/Login';

const isLoggedIn = sessionStorage.getItem("refresh-token")==null?false:true;

const App = () => {
  return (
    <div>
        <Navbar isLoggedIn={isLoggedIn}/>
        <Routes>
            <Route path="/" element={<Forum />}/>
            <Route path="/login" element={<Login />}/>
        </Routes>

    </div>
  )
}

export default App