var board = [
    [0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0],
    [0,0,0,0,0,0,0,0]
];
var tileSize;

function setup(){
    createCanvas(256,256);
    tileSize = width/board.length;
    noStroke();
    ellipseMode(CORNER);
}

function draw(){
    background(0);
    for(let row = 0; row < board.length; row++){
        for(let col = 0; col < board[row].length; col++){
            if((row + col) % 2){
                fill(255);
                rect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
            if(board[row][col] == 1){
                fill(100,150,255);
                ellipse(col * tileSize, row * tileSize, tileSize);
            }else if(board[row][col] == 2){
                fill(255,100,100);
                ellipse(col * tileSize, row * tileSize, tileSize);
            }
        }
    }
}

function mouseClicked(){
    let x = int(mouseX/tileSize);
    if(x < 0 || x > 7)
        return;
    let y = int(mouseY/tileSize);
    if(y < 0 || y > 7)
        return;
    board[y][x] = (board[y][x] + 1) % 3;
}

function keyPressed(){
    if(keyCode == 13){
        let output = "";
        for(let row = 0; row < 8; row++){
            for(let col = 0; col < 8; col++){
                if(board[row][col] == 0){
                    output += " ";
                }else if(board[row][col] == 1){
                    output += "X";
                }else{
                    output += ".";
                }
            }
            output += "\n";
        }
        console.log(output);
        copyToClipboard(output);
    }
}

const copyToClipboard = str => {
    const el = document.createElement('textarea');
    el.value = str;
    document.body.appendChild(el);
    el.select();
    document.execCommand('copy');
    document.body.removeChild(el);
  };