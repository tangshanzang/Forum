import React, {useState} from 'react'
import {getData, postData} from '../../service/ApiService'

function Login(props){
    const [inputs, setInputs] = useState({
        showError: false});

    const handleChange = (event) => {
        const value = event.target.value;
        const name = event.target.name;
        setInputs(values => ({...values, [name]: value}))
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        // getUsers();
        postLogin();
    }

    // const getUsers = () =>{
    //     getData("/api/user/users")
    //     .then(response => {
    //         return  response.json();
    //     }).then(data => {
    //         console.log(data);
    //     })
    // }

    const postLogin = () => {
        fetch('/api/login?username=' + inputs.username + '&password=' + inputs.password, {
            method: 'POST',
          }).then(response => {
            return response.json();
        }).then(data => {
            if(data.msg != null){
                setInputs(values => ({...values, showError: true}));
            }
            else{
                sessionStorage.setItem("access-token", data.access_token);
                sessionStorage.setItem("refresh-token", data.refresh_token);
                props.handleChangeProp(true);
                setInputs(values => ({...values, showError: false}));
            }
        })
    }

  return (
    <div className="app__login__Container">
        {
            inputs.showError === true &&
            <p className="app_login_error-message">User not found, please check your username and password</p>
        }
        <form className="app__login" onSubmit={handleSubmit}>
            <label htmlFor="username" className="app__login__label">Username</label>
            <input type="text" className="app__login__input"
                name="username" onChange={handleChange}/>  
            <label htmlFor="password" className="app__login__label">Password</label>
            <input type="text" className="app__login__input"
                name="password" onChange={handleChange}/>
            <button className="app_login_submit">login</button>
        </form>
    </div>
  )
}


export default Login