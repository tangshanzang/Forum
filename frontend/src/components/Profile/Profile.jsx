import React, {useState, useEffect} from 'react'
import {getData, deleteData} from '../../service/ApiService'
import { useNavigate } from 'react-router-dom';
import './Profile.css';

const Profile = (props) => {
    const [user, setUser] = useState({
        groups: [],
        threads: [],
        posts: []
    });
    const [tokens, setTokens] = useState({
    });

    const navigate = useNavigate();

    useEffect(() => {
        getUser();
        
    }, [])// eslint-disable-line react-hooks/exhaustive-deps

    // Send access token from sessionStorage
    const getUser = () =>{
        getData("/api/user/current", sessionStorage.getItem("access-token"))
        .then(response => {
            // If no access token, send refresh
            if(!response.ok) 
            throw new Error(response.status)

            return response.json();
        }).then(data => {
            setUser(data);
        }).catch(() => {
            getAccessToken();
            if(sessionStorage.getItem("access-token") !== null || sessionStorage.getItem("access-token") !== ""
            || sessionStorage.getItem("access-token") !== undefined){
                getData("/api/user/current", sessionStorage.getItem("access-token"))
                .then(response => {
                    // If no access token, send refresh
                    if(!response.ok) 
                    throw new Error(response.status)
        
                    return response.json();
                }).then(data => {
                    setUser(data);
                }).catch(() => {
                    console.log("Please relog")
                });
            }
        });
    }

    const getAccessToken = () =>{
        getData("/api/user/token/refresh", sessionStorage.getItem("refresh-token"))
        .then(response => {
            return  response.json();
        }).then(data => {
            // Save tokens
            sessionStorage.setItem("access-token", data.access_token);
            sessionStorage.setItem("refresh-token", data.refresh_token);
            console.log(data.access_token)
            console.log(sessionStorage.getItem("access-token"))
        })
    }

    const handleDelete = () =>{
        // console.log("clicked")
        deleteUser();
    }

    const deleteUser = () =>{
        console.log("called delete User")
        deleteData("/api/user/delete", sessionStorage.getItem("access-token"))
        .then(response => {
            console.log(response.status);
            if(response.status!==204) throw new Error(response.status);
            else return response;
        }).then(data => {
            console.log("called delete User - win")
            console.log(data)
        }).catch(() => {
            getAccessToken();
            console.log(sessionStorage.getItem("access-token"))
        })
    }
    
    

  return (
    <div className="app__profile__container">
        <div className="app__profile__element__container">
            <p className="app__profile__element__key">
                Name
            </p>
            <p className="app__profile__element__value">
                {user.name}
            </p>
        </div>

        <div className="app__profile__element__container">
            <p className="app__profile__element__key">
                Joined Since
            </p>
            <p className="app__profile__element__value">
                {user.createdTime}
            </p>
        </div>

        <div className="app__profile__element__container">
            <p className="app__profile__element__key">
                Status
            </p>
            <p className="app__profile__element__value">
                {user.status}
            </p>
        </div>

        <div className="app__profile__element__container">
            <p className="app__profile__element__key">
                Roles
            </p>
            <p className="app__profile__element__value">
                {user.roles}
            </p>
        </div>

        

        <div className="app__profile__element__container">
            <p className="app__profile__element__key">
                Group
            </p>
            <p className="app__profile__element__value">
                {user.groups.length}
            </p>
        </div>

        <div className="app__profile__element__container">
            <p className="app__profile__element__key">
                Thread
            </p>
            <p className="app__profile__element__value">
            {user.threads.length}
            </p>
        </div>

        <div className="app__profile__element__container">
            <p className="app__profile__element__key">
                Post
            </p>
            <p className="app__profile__element__value">
            {user.posts.length}
            </p>
        </div>

        <button onClick={() => handleDelete()}>Delete</button>
    </div>
  )
}

export default Profile