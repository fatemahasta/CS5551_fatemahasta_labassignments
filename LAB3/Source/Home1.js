
var app = angular.module("myVideoSearchApp", []);

app.controller("videoPageController", function($scope, $http) {
    $scope.searchSting = '';
    $scope.videos = [];
    $scope.translatedSearchString = '';

    $scope.getVideos = function () {
        var response = $http.get("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&q=" + $scope.searchSting + "&type=video&key=AIzaSyByiSJZq3F-tYBKz1jWVZlNMYlq7a-Yybk");
        response.then(function (response) {
            for (var i = 0; i < 10; i++) {
                var videoID = response.data.items[i].id.videoId;
                $scope.videos[i] = {
                    "videoId": response.data.items[i].id.videoId,
                    "snippet": response.data.items[i].snippet,
                    "videoEmbedUrl": 'http://www.youtube.com/embed/' + videoID,
                    "videoLaunchUrl": 'https://www.youtube.com/watch?v=' + videoID
                };
            }
        });
        getTranslation($scope.searchSting);


    };
    $scope.translatedSearchString;
    var getTranslation = function() {
        var response = $http.get("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170913T053303Z.4945e7cc7baee1a3.3a5624aea544f1494f6b42ec1d1ee681e74c235d&text="+$scope.searchSting+"&lang=en-ar");
        response.then(function (response) {
            $scope.translatedSearchString =response.data.text[0];

            return $scope.translatedSearchString;
            $scope.translatedSearchString =document.getElementById('mytext').value;
           // return translatedSearchString;
            //console.log(translatedSearchString);
            //return translatedSearchString;
            //print(translatedSearchString);
            // console.log(translatedSearchString);
            //return(translatedSearchString);
            // api call here
            // return translateString;
        });

    }
});

