import React, {useState} from 'react'
import {getData, postData} from '../../service/ApiService'

function Login(){
    const [inputs, setInputs] = useState("");

    const handleChange = (event) => {
        const value = event.target.value;
        const name = event.target.name;
        setInputs(values => ({...values, [name]: value}))
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        getUsers();
    }

    const getUsers = () =>{
        getData("/api/user/users")
        .then(response => {
            return  response.json();
        }).then(data => {
            console.log(data);
        })
    }

    const postLogin = () => {
        postData("/login", inputs)
            .then(response => {
                return response.json();
            }).then(data => {
                console.log(data);
            })
    }

  return (
    <form className="app__login" onSubmit={handleSubmit}>
        <label htmlFor="username" className="app__login__label">Username</label>
        <input type="text" className="app__login__input"
            name="username" onChange={handleChange}/>  
        <label htmlFor="password" className="app__login__label">Password</label>
        <input type="text" className="app__login__input"
            name="password" onChange={handleChange}/>
        <button className="app_login_submit">login</button>
    </form>
  )
}


export default Login