import React, { Component } from 'react';
import './editor.css'
import {
    Button,
    UserDisplay
} from '../components'
import * as firebase from "firebase";
import EditorBox from './editor-box';

// Configure FirebaseUI.
const firebaseUIConfig = {
    // Popup signin flow rather than redirect flow.
    signInFlow: 'popup',
    // Redirect to /signedIn after sign in is successful. Alternatively you can provide a callbacks.signInSuccess function.
    signInSuccessUrl: '/signedIn',
    // We will display Google and Facebook as auth providers.
    signInOptions: [
        firebase.auth.GoogleAuthProvider.PROVIDER_ID,
    ]
};

class EditorScreen extends Component {
    static ACTIVE_EDITOR_CONTENT_KEY = "activeEditorContent";
    constructor() {
        super();
        this.state = {
            signedIn: false,
            user: null,
            googleAccessToken: null,
            editorContent:""
        };

        this.setAuthStateListener();

        this.signOut = this.signOut.bind(this);
        this.syncEditorcontent = this.syncEditorcontent.bind(this);
        this.onEditorContentChanged = this.onEditorContentChanged.bind(this);
    }

    signOut() {
        firebase.auth().signOut().then(function() {
            // Sign-out successful.
        }).catch(function(error) {
            // An error happened.
        });
    }

    syncEditorcontent() {
        console.log(this.state.user);
        firebase.database().ref("users/" + this.state.user.uid + "/" + EditorScreen.ACTIVE_EDITOR_CONTENT_KEY).set(this.state.editorContent)
    };


    renderSignedInContent() {
        return (
            <div>
                <div>
                    <h1 className="inline nopad nomargin">Editor</h1>
                </div>

                <div className="text-right">
                    <UserDisplay
                        user={this.state.user}
                        signOut={this.signOut}/>
                </div>

                <Button type="success"
                        onClick={this.syncEditorcontent}>
                    <img
                        className="noheight"
                        src={require("../assets/img/ic_play_circle_filled_black_18dp/web/ic_play_circle_filled_black_18dp_1x.png")}/>
                    <span style={{verticalAlign:"middle"}}>
                        Update
                    </span>
                </Button>
                <EditorBox onChange={this.onEditorContentChanged}/>
            </div>
        );
    }

    onEditorContentChanged(editorContent) {
        this.setState({editorContent: editorContent})
    }

    renderLoginContent() {
        let provider = new firebase.auth.GoogleAuthProvider();
        let editorContext = this;
        let googleSignIn = () => {
            firebase.auth().signInWithPopup(provider).then(function(result) {
                editorContext.setState({
                    googleAccessToken:  result.credential.accessToken,
                    user: result.user
                });
            }).catch(function(error) {
                // Handle Errors here.
                let errorCode = error.code;
                let errorMessage = error.message;
                // The email of the user's account used.
                let email = error.email;
                // The firebase.auth.AuthCredential type that was used.
                let credential = error.credential;
            });
        };
        return (
            <div className="sign-in-root">
                <h2>Sign in to continue.</h2>
                <Button onClick={googleSignIn}>
                    Sign in with Google
                </Button>
            </div>
        );
    }
    render () {
        let content = null;
        if (this.state.signedIn) {
            content = this.renderSignedInContent();
        } else {
            content = this.renderLoginContent();
        }
        return (
            <div className="editor-root">
                {content}
            </div>
        );
    }

    setAuthStateListener() {
        let editorContext = this;
        firebase.auth().onAuthStateChanged(user => {
            editorContext.setState(
                {
                    signedIn: !!user,
                    user: user
                }
            );
        });
    }
}

export default EditorScreen;