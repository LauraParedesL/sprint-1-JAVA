const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            accounts: [],
            transactions: [],
            originAccount: "",
            destinationAccount: "",
            description: "",
            amount: 0,
            e : "",
            isMenuOpen: false
           
        }
    },
    created(){
       this.loadData()
    },

    methods : {
        loadData(){
            axios.get("/api/clients/current/accounts")
            .then(response => { 
                this.accounts = response.data
                console.log(this.accounts)
                
            })
            .catch(e => console.log(e))
        },
        logout(){
            axios.post("/api/logout")
            .then(response => { 
                console.log(response)
                window.location.href = "/web/html/index.html"
            })
            
        },
        createTransaction(){
            Swal.fire({
                title: "Are you sure?",
                text: "Verify that all data is correct!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#E6A51D",
                cancelButtonColor: "#000000",
                confirmButtonText: "Yes, transfer!"
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post("/api/transactions?amount=" + this.amount + 
                                "&description=" + this.description + 
                                "&originAccount=" + this.originAccount + 
                                "&destinationAccount=" + this.destinationAccount)
                    .then(result => {Swal.fire({
                            title: "Successfull Transfer!",
                            text: "",
                            icon: "success",
                            confirmButtonColor: "#E6A51D",
                          }).then((result) => {
                            if (result.isConfirmed){
                                window.location.href = "/web/html/accounts.html"
                            }
                          })
                        })
                    .catch(error => {Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: this.e = error.response.data,
                        confirmButtonColor: "#E6A51D"
                      });
                    console.log(error)
                    })
                }
              })
        },
        ontoggleMenu(){
            this.isMenuOpen = !this.isMenuOpen
        }
       

    }

})

app.mount("#app")