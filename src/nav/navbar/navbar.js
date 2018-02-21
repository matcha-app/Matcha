import React, { Component } from 'react';
import "./navbar.css";
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom'

class NavigationBar extends Component {

    static propTypes = {
        screen: PropTypes.array,
    };
    static defaultProps = {
        screens:[],
    };

    render() {
        let screenList = [];

        for (let i = 0; i < this.props.screens.length; ++i) {
            let screen = this.props.screens[i];
            screenList.push(
                <Link to={screen.path}
                      key={screen.name}>
                    <span className="navbar-link-box">
                        <span className="navbar-link-text">
                            {screen.name}
                        </span>
                </span>
                </Link>
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
    path = "";

    constructor (name, component, path) {
        this.name = name;
        this.view = component;
        if (path === undefined) {
            this.path = '\/' + encodeURIComponent(this.name.toLowerCase());
        } else {
            this.path = path;
        }
    }

};

export default NavigationBar;
