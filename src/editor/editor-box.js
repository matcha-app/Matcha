import React, { Component } from 'react';
import './editor.css';

class EditorBox extends Component {
    constructor() {
        super();
        this.state = {
            content: undefined
        };
        this.onInput = this.onInput.bind(this);
    }
    componentWillReceiveProps(nextProps) {
        if (nextProps.initialValue !== undefined) {
            this.setState({
                content: nextProps.initialValue
            })
        }
    }
    onInput(val) {
        this.setState({content: val});
        this.props.onChange(val);
    }
    render() {
        let renderEditor = this.state.content !== undefined;
        return (
            <div>
                <div className={renderEditor ? "fade invisible" : "fade visible"}>
                    <progress
                        style={{width:"100%"}}/>
                </div>
                <div onInput={() => this.onInput(this.editable.innerHTML)}
                     contentEditable={true}
                     className={["editor-box", "fade", renderEditor ? "visible" : "invisible"].join(' ')}
                     ref={(input) => {
                         this.editable = input
                     }}>
                    {this.state.content}
                </div>
            </div>
        )
    }
}

export default EditorBox;