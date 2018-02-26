import React, {Component} from 'react';
import "./user-display.css"
class UserDisplay extends Component {
    render() {
        return (
            <div className="user-display-root">
                <img className="user-display-img"
                     src={this.props.user.photoURL}
                     alt={this.props.user.displayName}/>

                <div className="user-display-info-root">
                    <p className="user-display-info-item">
                        {this.props.user.displayName}
                    </p>
                    <p className="user-display-info-item sign-out-button" onClick={this.props.signOut}>
                        Sign Out
                    </p>
                </div>

            </div>
        )
    }
}
export default UserDisplay;