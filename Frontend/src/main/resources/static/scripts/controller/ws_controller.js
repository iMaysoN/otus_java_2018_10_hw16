function WsCtrl($scope) {
    var vm = this;
    vm.users = [];
    vm.userForm = {
        id: null,
        surname: "",
        name: ""
    };

    angular.element('.modal').modal(
        {
            onCloseEnd: function() {
                _clearFormData();
                $scope.$apply();
            }
        }
    );
    setConnect();

    function setConnectedElements() {
        angular.element(".manipulate").toggleClass("disabled");
    }

    function setConnect() {
        stompClient = Stomp.over(new SockJS('/hw-ms-websocket'));
        stompClient.connect({}, function (frame) {
            setConnectedElements();
            sendConnect();
            console.log('connected: ' + frame);
            stompClient.subscribe('/topic/response', function (msg) {
                vm.setUsers(JSON.parse(msg.body).users)
            });
        });
    }

    vm.connectWs = function () {
        setConnect();
    };

    function sendConnect() {
        stompClient.send("/app/connect", {}, JSON.stringify({'method': 'connect'}));
    }

    vm.submitUser = function () {
        stompClient.send("/app/save", {}, JSON.stringify({'user': vm.userForm}));
        // _clearFormData();
    };

    vm.editUser = function (user) {
        vm.userForm.id = user.id;
        vm.userForm.surname = user.surname;
        vm.userForm.name = user.name;
    };

    vm.disconnectWs = function () {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnectedElements();
        console.log("Disconnected");
    };

    vm.deleteUser = function (user) {
        stompClient.send("/app/delete", {}, JSON.stringify({'user': user}));
    };

    vm.setUsers = function (users) {
        vm.users = users;
        $scope.$apply();
    };

    function _clearFormData() {
        vm.userForm.id = null;
        vm.userForm.surname = "";
        vm.userForm.name = "";
    }
}

angular
    .module('UserWsApp', [])
    .controller('WsCtrl', WsCtrl);
