import Config from "../Config";
import Vector2 from "./Vector2";

const DEBUG = Config.DEBUG;

class ImageObject{

    canvas;
    context;
    position;
    imagePath;
    image;
    width;
    height;
    scale;
    align; // default left
    baseline; // default top

    // multi state support
    isMultiState;
    maxState;
    state;

    constructor(imagePath : string|string[], x : number, y : number, scale?: number|undefined, width?: number|undefined, height?: number|undefined){
        this.canvas = document.querySelector('canvas');
        this.context = this.canvas!.getContext('2d');

        this.position = {
            x: x,
            y: y
        };
        
        this.imagePath = imagePath;

        if(imagePath instanceof Array){
            this.isMultiState = true;
            this.maxState = imagePath.length;
            this.image = this.createImageArray(imagePath);
        }else{
            this.isMultiState = false;
            this.maxState = 1;
            this.image = this.createImage(imagePath!);
        }
        this.state = 0;

        if(scale === undefined){
            this.scale = 1;
        }else{
            this.scale = scale;
        }

        if(width === undefined || height === undefined){
            if(this.image instanceof Array){
                this.width = (this.image[0]).width;
                this.height = (this.image[0]).height;
            }else{
                this.width = this.image.width;
                this.height = this.image.height;
            }
        }else{
            this.width = width;
            this.height = height;
        }

        this.align = "start";
        this.baseline = "top";
    }

    protected createImageArray(imagePaths : string[]){
        var images: HTMLImageElement[] = [];
        imagePaths.forEach(x =>{
            images.push(this.createImage(x));
        })
        return images;
    }

    protected createImage(imagePath : string){
        if(DEBUG) console.log(imagePath)

        const image = new Image();
        image.onload = function(){
            if(DEBUG) console.log(image)
        }
        image.src = imagePath;
        return image;
    }

    public draw(){
        if(DEBUG) console.log("draw image")
        var pos = this.position;
        var w = this.width;
        var h = this.height;
        var s = this.scale;

        if(this.align === "center"){
            pos.x -= w/2 * s;
        }else if(this.align === "end"){
            pos.x -= w  * s;
        }

        if(this.baseline === "middle"){
            pos.y -= h/2 * s;
        }else if(this.baseline === "bottom"){
            pos.y -= h * s;
        }

        if(this.image instanceof Array){
            this.context!.drawImage(this.image[this.state], pos.x, pos.y, w*s, h*s);
        }else{
            this.context!.drawImage(this.image, pos.x, pos.y, w*s, h*s);
        }
    }

    protected update(){
        this.draw();
    }

    public mouseInside(mousePos : {x : number, y : number}){

        var pos = this.position;
        var w = this.width;
        var h = this.height;
        if(this.align === "center"){
            pos.x -= w/2 * this.scale;
        }else if(this.align === "end"){
            pos.x -= w * this.scale;
        }

        if(this.baseline === "middle"){
            pos.y -= h/2 * this.scale;
        }else if(this.baseline === "bottom"){
            pos.y -= h * this.scale;
        }

        return (mousePos.x >= (pos.x)*Config.CANVAS_SCALE && mousePos.x <= (pos.x+w*this.scale)*Config.CANVAS_SCALE
            && mousePos.y >= (pos.y)*Config.CANVAS_SCALE && mousePos.y <= (pos.y+h*this.scale)*Config.CANVAS_SCALE);
    }

    public nextState(){
        if(this.image instanceof Array){
            this.state = (this.state+1) % this.maxState!;
        }
    }

    public setPosition(position : Vector2){
        this.position = position;
    }

    public setState(state : number){
        if(this.image instanceof Array){
            this.state = (state) % this.maxState!;
        }
    }

    public setAlign(align : "start" | "center" | "end"){
        this.align = align;
    }

    public setBaseLine(baseline : "top" | "middle" | "bottom"){
        this.baseline = baseline;
    }

    public setScale(scale: number){
        this.scale = scale;
    }

}


export default ImageObject;