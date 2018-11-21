import React from 'react'

function UserIdComponent(props) {
    return (
        <div class="user-block">
            <input type="text" placeholder={props.placeholder} class="user-id" />
        </div>
    );
}

export default UserIdComponent;
