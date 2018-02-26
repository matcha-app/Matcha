import React, { Component } from 'react';
import PropTypes from 'prop-types';
import "./button.css";

class Button extends Component {
    static DEFAULT = "default";
    static SUCCESS = "success";
    static WARNING = "warning";
    static DANGER =  "danger";
    static CANCEL = "cancel";
    static defaultProps = {
        type: "default"
    };
    render() {
        return (
            <button className={["btn", this.props.type].join(' ')}
                    {...this.props}>
                {this.props.children}
            </button>
        );
    }
}

Button.propTypes = {
    type: PropTypes.string
};

export default Button;