import React from 'react'
import UserIdComponent from './UserIdComponent';

class TargetUsersComponent extends React.Component {
    updateUserId = () => {
        console.log('test');
    }

    render() {
        return (
            <div class="target-users-block">
                <UserIdComponent placeholder="First User" lostFocusHandler={this.updateUserId} />
                <UserIdComponent placeholder="Second User" lostFocusHandler={this.updateUserId} />
                <div class="search-button-block">
                    <input type="button" value="Search" class="search-btn " />
                </div>
            </div>
        );
    }
}

export default TargetUsersComponent;
