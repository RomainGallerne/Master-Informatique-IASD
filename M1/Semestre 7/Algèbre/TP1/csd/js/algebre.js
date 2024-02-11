class Matrix4x4 {
    #coefs;   

    constructor() {
        this.#coefs = new Array();
        for (let i = 0; i < 4; i++) {
            this.#coefs.push(new Float32Array(4));
        }
        this.#coefs[0][0] = this.#coefs[1][1] = this.#coefs[2][2] = this.#coefs[3][3] = 1;
    }

    print() {
        let line = "";
        for (let i = 0; i < 4; i++) {
            line = "";
            for (let j = 0; j < 4; j++) {
                line += " "+ this.#coefs[i][j];
            }
            console.log(line);
        }
    }

    // crée la matrice de rotation d'angle alpha et d'axe i
    // multiplie la matrice receveuse par cette matrice de rotation
    // retourne la matrice receveuse transformée
    rotateX(alpha) {
        let r = new Matrix4x4();
        r.#coefs[1][1] = Math.cos(alpha);
        r.#coefs[1][2] = - Math.sin(alpha);
        r.#coefs[2][1] = Math.sin(alpha);
        r.#coefs[2][2] = Math.cos(alpha);
        return this.mult(r);
    }

    // multiplication (à droite) de this par m (Matrix4x4)
    // return this x m
    mult(m) {
        let tmp = new Matrix4x4();
        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                tmp.#coefs[i][j] = 0;   
                for (let k = 0; k < 4; k++) {
                    tmp.#coefs[i][j] += this.#coefs[i][k] * m.#coefs[k][j];
                }
            }           
        }

        for (let i = 0; i < 4; i++) {
            for (let j = 0; j < 4; j++) {
                this.#coefs[i][j] = tmp.#coefs[i][j];
            }
        }
        return this;
    }

    // multiplication (à droite) de this par v (Vecteur4x1)
    // return this x v
    transform(v) {
        let tmp = new Coords4x1;
        let sum = 0;
        for (let i = 0; i < 4; i++) {
            sum = 0;
            for (let j = 0; j < 4; j++) {
                sum += this.#coefs[i][j] * v.getCoef(j);
            }
            tmp.setCoef(i, sum);
        }
        v.setCoefs(tmp);
        return v;
    }

}

class Coords4x1 {
    #coefs;
    constructor() {
        this.#coefs = new Float32Array(4);
    }
    //affiche la transposée pour économiser l'espace d'affichage
    print() {
        let line = "";
        for (let i = 0; i < 4; i++) {
            line += " " + this.#coefs[i];
        }
        console.log(line);
    }

    rotateX(alpha) {
        let m = new Matrix4x4();
        m.rotateX(alpha);
        return m.transform(v);
    }

    getCoef(i) {
        return this.#coefs[i];
    }

    setCoefs(v2) {
        for (let i = 0; i < 4; i++) {
            this.#coefs[i] = v2.getCoef(i);
        }
    }

    setCoef(indice, value) {
        this.#coefs[indice] = value;
    }

}