/**
 * Created by user on 23/10/2016.
 */
var myapp = angular.module('demoMongo',[]);
myapp.run(function ($http) {
    // Sends this header with any AJAX request
    $http.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
    // Send this header only in post requests. Specifies you are sending a JSON object
    $http.defaults.headers.post['dataType'] = 'json'
});
myapp.controller('MongoRestController',function($scope,$http) {
    $scope.searchSting = '';
    $scope.videos = [];
    $scope.savedVideos = [];

    $scope.getVideos = function () {
        var response = $http.get("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&q=" + $scope.searchSting + "&type=video&key=AIzaSyByiSJZq3F-tYBKz1jWVZlNMYlq7a-Yybk");
        response.then(function (response) {
            for (var i = 0; i < 10; i++) {
                var videoID = response.data.items[i].id.videoId;
                $scope.videos[i] = {
                    "videoId": response.data.items[i].id.videoId,
                    "snippet": response.data.items[i].snippet,
                    "videoEmbedUrl": 'http://www.youtube.com/embed/' + videoID,
                    "videoLaunchUrl": 'https://www.youtube.com/watch?v=' + videoID,
                    "title": response.data.items[i].snippet.title,
                    "channelTitle": response.data.items[i].snippet.channelTitle,
                    "description": response.data.items[i].snippet.description
                };
            }
        });
    };

    $scope.insertData = function(index) {
        console.log($scope.videos[index].videoId);
        console.log($scope.videos[index].title);
        var dataParams = $scope.videos[index];
        var config = {
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        }
        var req = $http.post('/video-details', dataParams);
        req.success(function(data, status, headers, config) {
            $scope.message = data;
            console.log("inserted" + data);
            $scope.getAllData();
        });
        req.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });
    };

    $scope.currentEditContext = null;

    // enables edit mode
    $scope.editData = function(index) {
        $scope.currentEditContext = $scope.savedVideos[index];
    }

    // cancel edit mode
    $scope.cancelEdit = function (index) {
        $scope.currentEditContext = null;
        $scope.getAllData();
    }

    $scope.updateData = function () {
        var dataParams = $scope.currentEditContext;
        var videoId = $scope.currentEditContext['videoId'];
        var config = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        }
        var req = $http.put('/video-details/' + videoId, dataParams);
        req.success(function (data, status, headers, config) {
            $scope.message = data;
            $scope.getAllData();
            $scope.cancelEdit();
        });
        req.error(function (data, status, headers, config) {
            alert("failure message: " + JSON.stringify({ data: data }));
        });
    };

    $scope.deleteData = function (index) {
        var thisVideo = $scope.savedVideos[index];
        var videoId = thisVideo['videoId'];
        console.log('Sending request to delete video: ' + videoId);
        var config = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        }
        var req = $http.delete('/video-details/' + videoId);
        req.success(function (data, status, headers, config) {
            $scope.message = data;
            console.log(data);
            $scope.getAllData();
        });
        req.error(function (data, status, headers, config) {
            alert("failure message: " + JSON.stringify({ data: data }));
        });
    };

    $scope.getAllData = function () {
        console.log("Fetching saved video details");

        var config = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        }
        var req = $http.get('/video-details');
        req.success(function (data, status, headers, config) {
            $scope.savedVideos = data;
        });
        req.error(function (data, status, headers, config) {
            alert("Failed to fetch all video data: " + JSON.stringify({ data: data }));
        });
    };

    $scope.getAllData();
});
