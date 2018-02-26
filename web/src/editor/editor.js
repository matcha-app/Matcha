import React, { Component } from 'react';
import './editor.css'
import {
    Button,
    UserDisplay
} from '../components'
import * as firebase from "firebase";
import EditorBox from './editor-box';
import {
    Col,
    Row,
} from 'react-bootstrap';

class EditorScreen extends Component {
    static ACTIVE_EDITOR_CONTENT_KEY = "activeEditorContent";
    static LOG_TAG = EditorScreen.name;
    editorContentRef = null;

    constructor() {
        super();
        let user = firebase.auth().currentUser;
        let signedIn = user !== undefined && user !== null;

        this.state = {
            signedIn: signedIn,
            user: user,
            googleAccessToken: null,
            editorContent:"",
            editorInitialContent: undefined,
            editorUnsaved: false,
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

    onSignIn(user) {
        this.editorContentRef = firebase.database().ref("users/" + this.state.user.uid + "/" + EditorScreen.ACTIVE_EDITOR_CONTENT_KEY);

        this.editorContentRef.once('value').then(snapshot => {
            let initialContent = "";
            if (snapshot.val() !== null) {
                initialContent = snapshot.val()
            }
            this.setState(
                {
                    editorInitialContent: initialContent,
                    editorContent: initialContent
                }
            );
        })
    }

    syncEditorcontent() {
        this.editorContentRef.set(this.state.editorContent)
            .then(() => {
                this.setState({editorUnsaved: false});
                console.log(EditorScreen.LOG_TAG, "Saved editor content.");
            })
            .catch((error) => {
                console.log(error)
            });
    };

    renderSignedInContent() {
        return (
            <div>
                <div>
                    <h1 className="inline nopad nomargin">Editor</h1>
                </div>
                <Row>
                    <Col md={6}>
                        <Button type={this.state.editorUnsaved ? Button.WARNING : Button.SUCCESS }
                                onClick={this.syncEditorcontent}>
                            <img
                                className="noheight"
                                src={this.state.editorUnsaved ?
                                    require("../assets/img/ic_play_circle_filled_black_18dp/web/ic_play_circle_filled_orange_18dp_1x.png") :
                                    require("../assets/img/ic_play_circle_filled_black_18dp/web/ic_play_circle_filled_green_18dp_1x.png")}
                                alt=""/>
                            <span style={{verticalAlign:"middle"}}>
                                Update
                            </span>
                        </Button>
                    </Col>

                    <Col md={6} className="text-right">
                        <UserDisplay
                            user={this.state.user}
                            signOut={this.signOut}/>
                    </Col>
                </Row>


                <EditorBox
                    initialValue={this.state.editorInitialContent}
                    onChange={this.onEditorContentChanged}/>
            </div>
        );
    }

    onEditorContentChanged(editorContent) {
        this.setState({
            editorContent: editorContent,
            editorUnsaved: true
        })
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
                console.log(EditorScreen.LOG_TAG, error);
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
            if (user) {
                editorContext.onSignIn(user);
            }
        });
    }
}

export default EditorScreen;