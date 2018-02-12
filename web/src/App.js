import React, { Component } from 'react';
import './App.css';
import {
    Route,
    Redirect
} from 'react-router-dom';

import HomeScreen from './home/home';
import EditorScreen from "./editor/editor";
import NavigationBar from './nav/navbar/navbar'
import * as firebase from "firebase";

class App extends Component {
    screens = [];

    constructor() {
        super();

        let homeScreen = new NavigationBar.Screen("Home", HomeScreen, "/");
        let editorScreen = new NavigationBar.Screen("Editor", EditorScreen);
        this.screens.push(homeScreen, editorScreen);
        App.initializeFirebase();
    }


    static initializeFirebase() {
        let config = {
            apiKey: "AIzaSyCd-CNUQpdDyYUWSUxdMR0e-CK4KE6rA1k",
            authDomain: "matcha-80065.firebaseapp.com",
            databaseURL: "https://matcha-80065.firebaseio.com",
            projectId: "matcha-80065",
            storageBucket: "matcha-80065.appspot.com",
            messagingSenderId: "161299976395"
        };
        firebase.initializeApp(config);
    }

    render() {
        let routes = [];
        this.screens.forEach(screen => {
            routes.push(<Route key={screen.path} exact path={screen.path} component={screen.view}/>)
        });
        return (
            <div className="App">
                <NavigationBar
                    screens={this.screens}
                    onScreenSelect={this.onScreenSelect}
                />
                <div className="app-screen">
                    {routes}
                </div>
            </div>
        );
    }
}

export default App;
