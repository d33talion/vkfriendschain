import React from 'react'
import UserIdComponent from './UserIdComponent';

function TargetUsersComponent(props) {
    return (
        <div class="target-users-block">
            <div class="users-label-block">
                <p class="sm-2">Specify users ids</p>
            </div>
            <UserIdComponent placeholder="First User" />
            <UserIdComponent placeholder="Second User" />
            <div class="search-button-block">
                <input type="button" value="Search" class="search-btn " />
            </div>
        </div>
    );
}