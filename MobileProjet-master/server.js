var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');
let multer = require('multer')
let path = require('path')
const bcrypt = require('bcrypt');
var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'uploads');
    },
    filename: function (req, file, cb) {
        cb(null, `${file.fieldname}-${Date.now()}${path.extname(file.originalname)}`);
    }
});

var upload = multer({ storage: storage });


app.use(express.static("./public"))

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
extended: true
})); 

app.get('/', function(req,res){
    return res.send({error:true, message:'Test ReadHub Web API'})
});

var dbConn = mysql.createConnection({
    host: 'localhost',
    user:'root',
    password:'',
    database:'finalmobile'
});

dbConn.connect();

app.get('/allBook', function(req,res){
    dbConn.query('SELECT * FROM student', function(error, results, fields){
        if(error) throw error;
        return res.send(results);
    });
});

app.get('/login/:user_name/:password', async function(req, res){
    let user_name = req.params.user_name;
    let password = req.params.password;
    if(!user_name || !password){
        return res.status(400).send({error: "user error", message: 'Please provide username and password'});
    }
    dbConn.query('SELECT * FROM users WHERE user_name = ?', [user_name],
                            function(error, results, fields){
    if(error) throw error;
    if(results[0]){
        bcrypt.compare(password, results[0].password, function(error, result){
            if(error) throw error;
            if(results)
            {return res.send({"success":1, "user_id":results[0], "user_name":results[0].user_name})}
            else{return res.send({"success":0})}
        });
    }else{
        return res.send({"success":0})}
    });
});


app.post('/insertUser', async function(req, res){
    let post = req.body
    let user_name = post.user_name
    let email = post.email
    let password = post.password

    const salt = await bcrypt.genSalt(10)
    let password_hash = await bcrypt.hash(password, salt)

    if(!post){
        return res.status(400).send({error: true, message: 'Please provide username and password'})
    }

    dbConn.query('SELECT * FROM users WHERE user_name = ?', user_name, function(error, results, fields){
        if(error) throw error;
        if(results[0]){
            return res.status(400).send({error: true, message: 'This username is already in database.'});
        }else{
            var insertData = "INSERT INTO users(user_name, email, password) VALUES('"+
            user_name+"','"+email+"','"+password_hash+"')";

            dbConn.query(insertData, (error, results) => {
                if(error) throw error;
                return res.send(results);
            });
        }
    });
});

app.post('/addUserInterested', async function(req, res){
    let post = req.body
    let itemselected = post.itemselected

    dbConn.query('INSERT INTO user_interested (userinterested, user_id) VALUES (?, (SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1))', [itemselected], function(error, results, fields){
        if(error) throw error;
        return res.send(results);
    });
});
app.post("/insertUpload", upload.single('bookPart'), (req, res) => {
    var post = req.body;
    var fileBook = req.file;
    var book_name = post.book_name;
    var description = post.description;
    var writer_name = post.writer_name;
    var pub_name = post.pub_name;
    var catagory = post.catagory;

    if (!fileBook) {
        return res.status(400).send({ message: 'No file uploaded' });
    } else {
        const selectBtypeIdQuery = 'SELECT btype_id FROM book_type WHERE btype_name = ?';
        const values = [catagory];

        dbConn.query(selectBtypeIdQuery, values, (err, result) => {
            if (err) {
                console.error(err);
                return res.status(500).send({ message: 'Internal Server Error' });
            }

            if (result.length > 0) {
                const btypeId = result[0].btype_id;
                var fileBookPath = 'http://10.0.2.2/ReadHubApi/uploads/' + fileBook.filename;
                var insertData = "INSERT INTO book(book_name, description, pub_name, writer_name, btype_id, book_img) VALUES ('" + book_name + "','" + description + "','" + pub_name + "','" + writer_name + "','" + btypeId + "','" + fileBookPath + "')";

                dbConn.query(insertData, (err, result) => {
                    if (err) {
                        console.error(err);
                        return res.status(500).send({ message: 'Internal Server Error' });
                    }
                    return res.status(200).send({ message: 'File is successfully uploaded.', fileBook });
                });
            } else {
                return res.status(400).send({ message: 'Invalid category' });
            }
        });
    }
});



app.listen(3000, function(){
    console.log('Node app is running on port 3000');
});

module.exports = app;