import React, { Component } from 'react';
import "./home.css"
import Highlight from 'react-highlight';

class HomeScreen extends Component {

    render() {
        return(
            <div className="home-root">
                <h1 className="home-title">Matcha</h1>
                <p>Realtime Android UI designing on as many devices as you want.</p>


            </div>
        )
    }
}

export default HomeScreen;