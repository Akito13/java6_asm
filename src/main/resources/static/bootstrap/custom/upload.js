// const img = document.getElementById("bookImg");
// const mainImgInput = document.getElementById("img");
// const springImg = document.getElementById("springImg");
// const pickImgDiv = document.getElementById("pickImg");
// img.addEventListener("click", ev => {
//     mainImgInput.click();
// })
// function addSrcToImg(input) {
//     if(input.files && input.files[0]) {
//         let reader = new FileReader();
//         reader.readAsDataURL(input.files[0]);
//         reader.onload = function (e) {
//             img.src = e.target.result;
//             springImg.value = input.files[0].name;
//             pickImgDiv.classList.add("d-none");
//             img.classList.remove("d-none");
//         }
//     }
// }
//
// mainImgInput.addEventListener("change", function()  {
//     addSrcToImg(this);
// })
//
// pickImgDiv.addEventListener("click", function() {
//     img.click();
// })
const e=document.getElementById("bookImg"),n=document.getElementById("img"),t=document.getElementById("springImg"),i=document.getElementById("pickImg");e.addEventListener("click",(e=>{n.click()})),n.addEventListener("change",(function(){!function(n){if(n.files&&n.files[0]){let c=new FileReader;c.readAsDataURL(n.files[0]),c.onload=function(c){e.src=c.target.result,t.value=n.files[0].name,i.classList.add("d-none"),e.classList.remove("d-none")}}}(this)})),i.addEventListener("click",(function(){e.click()}));