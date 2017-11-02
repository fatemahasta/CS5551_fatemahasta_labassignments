/**
 * Fatema Hasta
 */

var MongoClient = require('mongodb').MongoClient;
var assert = require('assert');
var bodyParser = require("body-parser");
var express = require('express');
var cors = require('cors');
var app = express();
var path = require('path');
var url = 'mongodb://root:root123@ds125255.mlab.com:25255/asedemo';

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use('/public', express.static(__dirname + '/public'));

// receive request to insert data
app.post('/video-details', function (req, res) {
    MongoClient.connect(url, function(err, db) {
        if(err)
        {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }
        insertDataToDB(db, req.body,
            function() {
                res.write("Successfully inserted");
                res.end();
            },
            function(err) {
                res.write("Insert failed with error " + err);
                res.end();
            }
        );
    });
})
// helper function for data insert
var insertDataToDB = function (db, data, successCallback, errorCallback) {
    db.collection('video-details').insertOne(data, function (err, result) {
        if (err) {
            errorCallback(err);
        } else {
            console.log("Inserted a document into the asedemo collection.");
            successCallback();
        }
    });
};

// receive request to update data
app.put('/video-details/:id', function (req, res) {
    var videoId = req.params.id;
    var updateObject = {
        "videoLaunchUrl": req.body['videoLaunchUrl'],
        "title": req.body['title'],
        "channelTitle": req.body['channelTitle']
    };
    console.log('Sending request with updated params: ' + updateObject.channelTitle);
    MongoClient.connect(url, function (err, db) {
        if (err) {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }
        updateDataInDB(db, videoId, updateObject,
            function (updatedDetails) {
                res.write("Update success:" + updatedDetails);
                res.end();
            },
            function (errorDetails) {
                res.write("Failed to update: " + errorDetails);
                res.end();
            }
        );
    });
})
// helper function to update data
var updateDataInDB = function (db, videoId, updateParams, successCallback, errorCallback) {
    var paramsToUpdate = { "videoLaunchUrl": updateParams.videoLaunchUrl, "title": updateParams.title, "channelTitle": updateParams.channelTitle };
    var updatedDetails = db.collection('video-details').findAndModify(
        {"videoId": videoId},
        null,
        {$set: paramsToUpdate},
        { new: true }
    );
    updatedDetails.then(function(data) {
        successCallback(data);
    }).catch(function(err) {
        errorCallback(err);
    })
};

// receive request to delete data
app.delete('/video-details/:id', function (req, res) {
    var videoId = req.params.id;
    console.log('Sending mongo request to delete video: ' + videoId);
    MongoClient.connect(url, function (err, db) {
        if (err) {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }
        // delete the record here
        deleteDataFromDB(db, videoId,
            function (result) {
                res.write("Successfully deleted " + result + " record(s)");
                res.end();
            },
            function (result) {
                res.write("Failed to deleted record(s). Error: " + result);
                res.end();
            }
        );
    });
})
// helper function to delete data
var deleteDataFromDB = function (db, videoId, successCallback, errorCallback) {
    db.collection('video-details').deleteOne({"videoId": videoId})
        .then(function(data) {
            successCallback(data);
        }).catch(function(data) {
            errorCallback(data);
        })
};

// receive request to get all data
app.get('/video-details', function (req, res) {
    MongoClient.connect(url, function (err, db) {
        if (err) {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }
        // retrieve the record here
        findDataFromDB(db, req.body,
            function (result) {
                res.send(result);
            },
            function (result) {
                res.send("Failed to fetch data");
            }
        );
    });
})
// helper function to find all data
var findDataFromDB = function (db, requestBody, successCallback, errorCallback) {
    var cursor = db.collection('video-details').find();
    var result = cursor.toArray();
    result.then(function(data) {
        successCallback(data);
    });
};


app.get('/', function (req, res) 
{
  res.sendFile(path.resolve('Home.html'));
})

app.listen(process.env.PORT || 5000, function() {
	console.log('app running')
})

//var server = app.listen(process.env.PORT || 5000,function () {
//    var host = server.address().address
//    var port = server.address().port

//    console.log("Example app listening at http://%s:%s", host, port)
//})
