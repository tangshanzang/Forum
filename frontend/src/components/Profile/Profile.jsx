import React, {useState, useEffect} from 'react'
import {getData, postData} from '../../service/ApiService'
import ProfileElement from './ProfileElement/ProfileElement'
import './Profile.css';

const Profile = () => {
    const [user, setUser] = useState({
        groups: [],
        threads: [],
        posts: []
    });

    useEffect(() => {
        getUser();
    }, [])

    // Send access token from sessionStorage
    const getUser = () =>{
        getData("/api/user/current", sessionStorage.getItem("access-token"))
        .then(response => {
            // If no access token, send refresh
            if(!response.ok) 
            throw new Error(response.status)

            return response.json();
        }).then(data => {
            console.log(data)
            setUser(data);
        }).catch(() => {
            getAccessToken();
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
            // Retry
            getUser();
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
    </div>
  )
}

export default Profile