import React, { Component } from 'react';
import "./navbar.css";

class NavigationBar extends Component {
    render() {
        let screenList = [];

        for (let i = 0; i < this.props.screens.length; ++i) {
            let screen = this.props.screens[i];
            screenList.push(
                <span className="navbar-link-box"
                      key={screen.name}
                      onClick={() => {this.onScreenSelect(i)}}>
                    <span className="navbar-link-text">
                        {screen.name}
                    </span>
                </span>
            );
        }

        return (
            <div className="navbar-root">
                {screenList}
            </div>
        );
    }

    onScreenSelect(index) {
        this.props.onScreenSelect(index);
    }
}
NavigationBar.Screen = class {
    name = "";
    view = null;

    constructor (name, component) {
        this.name = name;
        this.view = component;
    }

};

export default NavigationBar;
