import React from 'react'
import UserIdComponent from './UserIdComponent';

function TargetUsersComponent(props) {
    return (
        <div class="target-users-block">
            <UserIdComponent placeholder="First User" />
            <UserIdComponent placeholder="Second User" />
            <div class="search-button-block">
                <input type="button" value="Search" class="search-btn " />
            </div>
        </div>
    );
}

export default TargetUsersComponent;
