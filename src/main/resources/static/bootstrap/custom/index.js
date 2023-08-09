const removeBtn = document.getElementById("removable")
const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));
const removeBook = function (confirmRemove, bookId, quantity) {
    angular.element(confirmRemove).scope().cart.removeOrder(bookId, quantity);
    const modal = bootstrap.Modal.getInstance(document.getElementById("confirmModal"));
    modal.hide();
    $('.modal-backdrop').remove();
}
document.addEventListener('DOMContentLoaded', function() {
    removeBtn?.addEventListener("click", e => {
        const target = e.target;
        if(target.type==="button") {
            const modalContent = document.getElementById("modalContent");
            const pattern = /(\d{1,2})/g;
            const bookIds = target.id.match(pattern);
            if(bookIds) {
                const bookId = bookIds[0];
                const confirmRemove = document.getElementById("confirmRemove");
                if(removeBtn.classList.contains("admin")) {
                    confirmRemove.href = `/admin/book/remove/${bookId}`;
                } else {
                    confirmRemove.addEventListener("click", removeBook.bind(null, confirmRemove, bookId, 1), {once: true})
                    // angular.element(confirmRemove).scope().cart.removeOrder();
                    // confirmRemove.href = `/cart/remove/${bookId}?quantity=1`;
                }
                const bookName = document.getElementById(`book-${bookId}`).innerText;
                console.log(`Book name: ${bookName}`)
                modalContent.innerText = `Remove ${bookName} ?`
            }

        }
    })
});
// const e=document.getElementById("removable");[...document.querySelectorAll('[data-bs-toggle="tooltip"]')].map((e=>new bootstrap.Tooltip(e)));e?.addEventListener("click",(t=>{const o=t.target;if("button"===o.type){const t=document.getElementById("modalContent"),n=/(\d{1,2})/g,m=o.id.match(n);if(m){const o=m[0],n=document.getElementById("confirmRemove");e.classList.contains("admin")?n.href=`/admin/book/remove/${o}`:n.href=`/cart/remove/${o}?quantity=1`;const c=document.getElementById(`book-${o}`).innerText;console.log(`Book name: ${c}`),t.innerText=`Remove ${c} ?`}}}));
let app = angular.module("myapp", ["ngRoute"]);
app.controller("cart-ctrl", function ($scope, $http){
    const modal = document.getElementById("confirmModal");
    modal.addEventListener("hidden.bs.modal", e => {
        console.log("removed event from modal button");
        removeEventListener("click", removeBook);
    })
    $scope.cart = {
        // loadFromLocalStorage() {
        //     const user = JSON.parse(localStorage.getItem("user"));
        //     const orders = user != null ? JSON.parse(localStorage.getItem(`cart${user.maKH}`)) : null;
        //     if(orders != null){
        //         this.payload = {};
        //         this.payload.orders = orders;
        //         this.payload.user = user;
        //     }
        //     else {
        //         this.getCart();
        //     }
        // },
        saveCartToLocal: function (isClear) {
            localStorage.setItem("user", JSON.stringify(this.payload.user));
            isClear ? localStorage.removeItem(`cart${this.payload.user.maKH}`)
                    : localStorage.setItem(`cart${this.payload.user.maKH}`, JSON.stringify(this.payload.orders));
        },
        calTotal() {
            if(this.payload === undefined || this.payload.orders === undefined) return 0.0;
            return Object.values(this.payload.orders).reduce((previousValue, {gia, soLuongMua})=> previousValue + soLuongMua * gia, 0);
        },
        getCount(orders) {
          return Object.values(orders).reduce((previousValue, {soLuongMua}) => previousValue + soLuongMua, 0);
        },
        getCart() {
            $http.get("/api/cart").then(res => {
                if (res.status === 200) {
                    this.payload = res.data;
                    let isClear = false;
                    if(Object.keys(this.payload.orders).length === 0)
                        isClear = true;
                    this.saveCartToLocal(isClear);
                }
                console.log(this.payload.orders);
            }).catch(err => {
                console.error(err);
            });
        },
        checkOrders: function () {
            let totalCount = this.getCount(this.payload.orders);
            let isClear = false;
            $("#totalCount").text(totalCount);
            if (totalCount === 0) {
                this.payload.orders = undefined;
                isClear = true;
                window.location.reload();
            }
            this.saveCartToLocal(isClear);
        },
        removeOrder(id, quantity) {
            console.log("Removing shet...");
            console.log("Book id is: " + id);
            console.log("Quantity is: " + quantity);
            $http.get(`/api/cart/remove/${id}?quantity=${quantity}`)
                .then(res => {
                    if(res.status === 200) {
                        this.payload = res.data;
                        this.checkOrders();
                    }
                    console.log(this.payload);
                })
                .catch(err => console.error(err));
        },
        clear() {
            $http.get(`/api/cart/clear`)
                .then(res => {
                    if(res.status === 200) {
                        this.payload = res.data;
                        this.checkOrders();
                        // this.saveCartToLocal();
                    }
                    console.log(this.payload);
                })
                .catch(err => console.error(err));
        },
        checkout() {
            $http.get("/api/cart/checkout")
                .then(res => {
                    if(res.status === 200) {
                        this.payload = res.data;
                        this.checkOrders();
                        // this.saveCartToLocal();
                    }
                })
                .catch(err => console.error(err))
        }
    }
    $scope.cart.getCart();
})