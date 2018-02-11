import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import HomeScreen from './home/home';
import NavigationBar from './nav/navbar/navbar'

class App extends Component {
    navigationBar = null;
    screens = [];

    constructor() {
        super();

        let homeScreen = new NavigationBar.Screen("Home", HomeScreen);
        this.screens.push(homeScreen);

        this.navigationBar = new NavigationBar(this.screens);

        this.state = {
            screenIndex:0,
        };
        this.onScreenSelect = this.onScreenSelect.bind(this);
    }

    onScreenSelect(index) {
        this.setState({screenIndex:index});
    }

    render() {
        let navigationBar = new NavigationBar();
        let ActiveScreen = this.screens[this.state.screenIndex].view;
        return (
            <div className="App">
                <NavigationBar
                    screens={this.screens}
                    onScreenSelect={this.onScreenSelect}
                />
                <ActiveScreen />
            </div>
        );
    }
}

export default App;
