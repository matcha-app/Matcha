import React, { Component } from 'react';
import './editor.css';

class EditorBox extends Component {
    constructor() {
        super();
        this.state = {
            content: undefined
        };
        this.onInput = this.onInput.bind(this);
        this.onKeyDown = this.onKeyDown.bind(this);

    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.initialValue !== undefined && this.state.content === undefined) {
            this.setState({
                content: nextProps.initialValue
            })
        }
    }

    onInput(event) {
        let val = event.target.value;
        this.setState({content: val});
        this.props.onChange(val);
    }

    onKeyDown(event) {
        if (event.keyCode === 9) {
            let value = this.editor.value;
            let start = this.editor.selectionStart;
            let end = this.editor.selectionEnd;

            this.editor.value = value.substring(0, start) + "\t" + value.substring(end);
            this.editor.selectionStart = start + 1;
            this.editor.selectionEnd = start + 1;

            event.preventDefault();
            event.stopPropagation();
        }
        return false;
    }
    render() {
        let renderEditor = this.state.content !== undefined;
        return (
            <div>
                <div className={renderEditor ? "fade invisible" : "fade visible"}>
                    <progress
                        style={{width:"100%"}}/>
                </div>

                <textarea
                    value={this.state.content}
                    onChange={this.onInput}
                    onKeyDown={this.onKeyDown}
                    className={["editor-box", "fade", renderEditor ? "visible" : "invisible"].join(' ')}
                    ref={input => {this.editor = input}}>
                </textarea>
            </div>
        )
    }
}

export default EditorBox;