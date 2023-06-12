const img = document.getElementById("bookImg");
const mainImgInput = document.getElementById("img");
const springImg = document.getElementById("springImg");
const pickImgDiv = document.getElementById("pickImg");
img.addEventListener("click", ev => {
    mainImgInput.click();
})
function addSrcToImg(input) {
    if(input.files && input.files[0]) {
        let reader = new FileReader();
        reader.readAsDataURL(input.files[0]);
        reader.onload = function (e) {
            img.src = e.target.result;
            springImg.value = input.files[0].name;
            pickImgDiv.classList.add("d-none");
            img.classList.remove("d-none");
        }
    }
}

mainImgInput.addEventListener("change", function()  {
    addSrcToImg(this);
})

pickImgDiv.addEventListener("click", function() {
    img.click();
})