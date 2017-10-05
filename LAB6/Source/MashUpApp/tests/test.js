describe('homeCtrl', function() {
	var scope, httpBackend;
	
	beforeEach(angular.mock.module('app'));
	beforeEach(angular.mock.inject(function($rootScope, $controller, $httpBackend, $http) {
		scope = $rootScope.$new();
        httpBackend = $httpBackend;
        //httpBackend.when("GET", "http://127.0.0.1:8100/#/login").respond([{}, {}, {}]);
        $controller('homeCtrl', {$scope: scope,$http: $http});
	}));

	it("checks whether events are getting generated or not", function () {
		/*httpBackend.flush();*/
        scope.search();
       alert(scope.data);
       expect(scope.data.length).toBe(3);
	});
});


describe('registerCtrl', function() {
  var $scope, controller, $location, $window;

  beforeEach(module('app'));

  beforeEach(function() {
    angular.module('myApp').value('$localStorage', {})
  })

  beforeEach(inject(function($rootScope, $controller, _$window_) {
    $scope = $rootScope.$new();
    $window = _$window_;
    $location = jasmine.createSpyObj('$location', ['url']);
    controller = $controller('registerCtrl', {
      $scope: $scope,
      $location: $location,
      $window: $window
    });
  }));


  it('calls alert if user has not privilages', inject(function($httpBackend) {
    
    spyOn($window, 'alert');
    $httpBackend.expectPOST('/log').respond(200, {
      access: {
        state: 202
      }
    });    
    $scope.log();    
    $httpBackend.flush();
    expect($window.alert).toHaveBeenCalledWith('Sorry you don\'t have permission')
  }));

})


describe("Unit Testing Examples", function() {

  beforeEach(angular.mock.module('app'));

  it('should have a LoginCtrl controller', function() {
    expect(App.LoginCtrl).toBeDefined();
  });
  
});



describe("Unit Testing Examples", function() {

  beforeEach(angular.mock.module('App'));

 

  it('should have a working LoginService service', inject(['Eventful',
    function(LoginService) {
      expect(LoginService.isValidEmail).not.to.equal(null);

      // test cases - testing for success
      var validEmails = [
        'test@test.com',
        'test@test.co.uk',
        'test734ltylytkliytkryety9ef@jb-fe.com'
      ];

      // test cases - testing for failure
      var invalidEmails = [
        'test@testcom',
        'tes@t@test.com',
        ''
      ];

      // you can loop through arrays of test cases like this
      for (var i in validEmails) {
        var valid = LoginService.isValidEmail(validEmails[i]);
        expect(valid).toBeTruthy();
      }
      for (var i in invalidEmails) {
        var valid = LoginService.isValidEmail(invalidEmails[i]);
        expect(valid).toBeFalsy();
      }

    }])
  );
});





describe('testController', function () {
    var $controller, $scope, AuthenticationService;
    var dt = {un: "fatemahasta95@gmail.com", pwd: "hammy"};

   beforeEach(module('app', {
  LoginService : { Login: jasmine.createSpy().and.returnValue(dt) }
		}));

    beforeEach(inject(function(_$controller_, $rootScope, _LoginService_){
        $scope = $rootScope.$new();
        LoginService = _LoginService_;
        $controller = _$controller_("LoginController", {
            $scope : $scope,
            AuthenticationService : AuthenticationService
        })
    }));

    it("should load the service", function(){
        expect(LoginService.Login).toHaveBeenCalled();
    })
});

