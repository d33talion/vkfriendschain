import React from 'react'

function UserIdComponent(props) {
    return (
        <div className="user-block">
            <input
                type="text"
                placeholder={props.placeholder}
                className="user-id"
                onBlur={() => props.lostFocusHandler()} />
        </div>
    );
}

export default UserIdComponent;
