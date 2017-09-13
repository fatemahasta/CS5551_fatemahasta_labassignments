function LoginController($scope) {

    $scope.logins = [];
    $scope.login = function (Username, Password) {
        localStorage.setItem("name" , user);
        $scope.logins.push( localStorage.getItem("name") + " was logged in.");
    };
}