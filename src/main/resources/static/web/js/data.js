const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            modal: false,
            modal2: false,
            name: "",
            lastName : "",
            email : "",
            password : "" ,
            e : "" 
        }
    },
    

    methods : {
        mostrarModal(){
            this.modal= true
        },
        cerrarModal(){
            this.modal= false
        },

        mostrarModal2(){
            this.modal2= true
        },
        cerrarModal2(){
            this.modal2= false
        },

        login(){
            axios.post("/api/login?email=" +this.email+ "&password="+ this.password)
            .then(response => { 
                console.log(response)
                window.location.href = "/web/html/accounts.html"
            })
            .catch(error => 
                {console.log(error)
                    Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
                footer: 'All fields must to be filled out'
                  })}
              )
        },

        signUp(){
            axios.post("/api/clients?name=" + this.name + "&lastName=" + this.lastName + "&email=" + this.email
                         + "&password=" + this.password)
            .then(response => {
                console.log(response)
                this.login()
            } )
            .catch(error => 
                {
                    Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
                footer: 'All fields must to be filled out'
                  })}
              )
        },
        

        clearData(){
            this.email = ""
            this.password = ""
            this.name = ""
            this.lastName = ""
        }
    

        
    }

})

app.mount("#app")