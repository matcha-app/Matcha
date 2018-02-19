import React, { Component } from 'react';
import './editor.css';

class EditorBox extends Component {
    render() {
        return (
            <div onInput={() => this.props.onChange(this.editable.innerHTML)}
                 contentEditable={true}
                 className="editor-box"
                 ref={(input) => {this.editable = input}}>
            </div>
        )
    }
}

export default EditorBox;