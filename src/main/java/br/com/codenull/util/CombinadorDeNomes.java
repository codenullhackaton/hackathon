package br.com.codenull.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Daniel Franco
 * @since 27/08/2016 18:09
 */
public class CombinadorDeNomes {

    private static final String CLINICA_MEDICA = "Clínica Médica (Medicina interna)";
    private static final int QTD_COOPERADOS = 10;

    private static final List<String> nomes = Arrays.asList("Maria ", "Davi ", "João ", "Miguel ", "Pedro ", "Arthur ",
        "Ana ", "Alice ", "Gabriel ", "Lucas ", "Laura ", "Júlia ", "Bernardo ", "Heitor ", "Enzo ", "Valentina ",
        "Guilherme ", "Helena ", "Sophia ", "Rafael ", "Sofia ", "Manuela ", "Beatriz ", "Gustavo ", "Matheus ",
        "Lorenzo ", "Samuel ", "Luiza ", "Mariana ", "Henrique ", "Theo ", "Isabella ", "Lara ", "Felipe ", "Livia ",
        "Nicolas ", "Daniel ", "Heloisa ", "Isabela ", "Leonardo ", "Letícia ", "Lorena ", "Giovanna ", "Isadora ",
        "Eduardo ", "Luiz ", "Anna ", "José ", "Luisa ", "Rafaela ");
    private static final List<String> sobrenomes = Arrays.asList("Alves ", "Monteiro ", "Novaes ", "Mendes ", "Barros ",
        "Freitas ", "Barbosa ", "Pinto ", "Moura ", "Cavalcanti ", "Dias ", "Castro ", "Campos ", "Cardoso ", "Silva ",
        "Souza ", "Costa ", "Santos ", "Oliveira ", "Pereira ", "Rodrigues ", "Almeida ", "Nascimento ", "Lima ",
        "Araújo ", "Fernandes ", "Carvalho ", "Gomes ", "Martins ", "Rocha ", "Ribeiro ", "Rezende ", "Sales ",
        "Peixoto ", "Fogaça ", "Porto ", "Ribeiro ", "Duarte ", "Moraes ", "Ramos ", "Pereira ", "Ferreira ",
        "Silveira ", "Moreira ", "Teixeira ", "Caldeira ", "Vieira ", "Nogueira ", "da Costa ", "da Rocha ", "da Cruz ",
        "da Cunha ", "da Mata ", "da Rosa ", "da Mota ", "da Paz ", "da Luz ", "da Conceição ", "das Neves ",
        "Fernandes ", "Gonçalves ", "Rodrigues ", "Martins ", "Lopes ", "Gomes ", "Mendes ", "Nunes ", "Carvalho ",
        "Melo ", "Cardoso ", "Pires ", "Jesus ", "Aragão ", "Viana ", "Farias ");
    private static final List<String> UFs = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
        "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
    private static final List<String> especialidades = Arrays.asList("Acupuntura", "Administração em Saúde",
        "Alergia e Imunologia", "Alergia e Imunologia Pediátrica", "Anestesiologia", "Angiologia",
        "Angiorradiologia e Cirurgia Endovascular", "Atendimento ao Queimado", "Cardiologia", "Cardiologia Pediátrica",
        "Cirurgia Cardiovascular", "Cirurgia Crânio-Maxilo-Facial", "Cirurgia da Mão", "Cirurgia de cabeça e pescoço",
        "Cirurgia do Aparelho Digestivo", "Cirurgia do trauma", "Cirurgia Geral", "Cirurgia Pediátrica",
        "Cirurgia Plástica", "Cirurgia Torácica", "Cirurgia Vascular", "Cirurgia videolaparoscópica", "Citopatologia",
        CLINICA_MEDICA, "Coloproctologia", "Densitometria óssea", "Dermatologia", "Dor",
        "Ecocardiografia", "Ecografia vascular com doppler", "Eletrofisiologia clínica invasiva", "Endocrinologia",
        "Endocrinologia pediátrica", "Endoscopia", "Endoscopia digestiva", "Endoscopia ginecológica",
        "Endoscopia respiratória", "Ergometria", "Foniatria", "Gastroenterologia", "Gastroenterologia pediátrica",
        "Genética médica", "Geriatria", "Ginecologia e obstetrícia", "Hansenologia", "Hematologia e Hemoterapia",
        "Hematologia e hemoterapia pediátrica", "Hemodinâmica e cardiologia intervencionista", "Hepatologia",
        "Homeopatia", "Infectologia", "Infectologia hospitalar", "Infectologia pediátrica", "Mamografia", "Mastologia",
        "Medicina de Família e Comunidade", "Medicina de urgência", "Medicina do adolescente", "Medicina do Trabalho",
        "Medicina do Tráfego", "Medicina Esportiva", "Medicina fetal", "Medicina Física e Reabilitação",
        "Medicina Intensiva", "Medicina intensiva pediátrica", "Medicina Legal e Perícia Médica", "Medicina Nuclear",
        "Medicina Preventiva e Social", "Nefrologia", "Nefrologia pediátrica", "Neonatologia", "Neurocirurgia",
        "Neurofisiologia clínica", "Neurologia", "Neurologia pediátrica", "Neurorradiologia",
        "Nutrição parenteral e enteral", "Nutrição parenteral e enteral pediátrica", "Nutrologia",
        "Nutrologia pediátrica", "Obstetrícia", "Oftalmologia", "Oncologia", "Ortopedia e Traumatologia",
        "Otorrinolaringologia", "Patologia", "Patologia Clínica", "Pediatria", "Pneumologia", "Pneumologia pediátrica",
        "Psicogeriatria", "Psicoterapia", "Psiquiatria", "Psiquiatria da infância e adolescência", "Psiquiatria forense",
        "Radiologia e Diagnóstico por Imagem", "Radiologia intervencionista e angiorradiologia", "Radioterapia",
        "Reumatologia", "Reumatologia pediátrica", "Transplante de medula óssea",
        "Ultrassonografia em ginecologia e obstetrícia", "Urologia");
    private static final List<String> procedimentos = Arrays.asList("Consulta Inicial", "Retorno Consulta", "Consulta Emergencial");
    private static final List<String> cooperados = new ArrayList<>(QTD_COOPERADOS);
    private static final List<String> enderecos = Arrays.asList("Alameda Antônio Andrade",
        "Alameda Doutor João Paulino",
        "Alameda José Meneguetti",
        "Alameda Ney Aminthas Barros Braga",
        "Avenida 19 de Dezembro",
        "Avenida 7 de Setembro",
        "Avenida Advogado Horácio Raccanello Filho",
        "Avenida Alziro Zarur",
        "Avenida Ambrósio Bulla",
        "Avenida Américo Belay",
        "Avenida Anchieta",
        "Avenida Antônio Santiago Gualda",
        "Avenida Arquiteto Nildo Ribeiro da Rocha",
        "Avenida Bento Munhoz da Rocha Netto",
        "Avenida Bertioga",
        "Avenida Brasil",
        "Avenida Carlos Correia Borges",
        "Avenida Carlos Gomes",
        "Avenida Carmen Miranda",
        "Avenida Carneiro Leão",
        "Avenida Centenário",
        "Avenida Cerro Azul",
        "Avenida Cidade de Leiria",
        "Avenida Colombo",
        "Avenida Constâncio Pereira Dias",
        "Avenida Curitiba",
        "Avenida Demétrio Ribeiro",
        "Avenida Deputado José Alves dos Santos",
        "Avenida Dona Maria Gaspar Pedrosa Moleirinho",
        "Avenida Dona Sophia Rasgulaeff",
        "Avenida Doutor Alexandre Rasgulaeff",
        "Avenida Doutor Gastão Vidigal",
        "Avenida Doutor Luiz Teixeira Mendes",
        "Avenida Doutor Mário Clapier Urbinati",
        "Avenida Doutor Vladimir Babkov",
        "Avenida Duque de Caxias",
        "Avenida Euclides da Cunha",
        "Avenida Francisca do Amaral Mello Maluf",
        "Avenida Francisco Ferreira de Miranda",
        "Avenida Francisco Sebriam Madrid",
        "Avenida Franklin Delano Roosevelt",
        "Avenida Getúlio Vargas",
        "Avenida Governador Parigot de Souza",
        "Avenida Guaiapó",
        "Avenida Guaíra",
        "Avenida Guedner",
        "Avenida Harry Prochet",
        "Avenida Herval",
        "Avenida Humaitá",
        "Avenida Independência",
        "Avenida Itororó",
        "Avenida Jinroku Kubota",
        "Avenida Joaquim Duarte Moleirinho",
        "Avenida José Alves Nendo",
        "Avenida José Eustatios Kotsifas",
        "Avenida João Paulino Vieira Filho",
        "Avenida Júlio Limonta",
        "Avenida Kakogawa",
        "Avenida Laguna",
        "Avenida Londrina",
        "Avenida Lucílio de Held",
        "Avenida Mandacaru",
        "Avenida Marcelo Messias Busiquia",
        "Avenida Mauá",
        "Avenida Melvim Jones",
        "Avenida Monteiro Lobato",
        "Avenida Morangueira",
        "Avenida Naihma Name",
        "Avenida Nóbrega",
        "Avenida Osíres Stenghel Guimarães",
        "Avenida Paissandu",
        "Avenida Papa João XXIII",
        "Avenida Paranavaí",
        "Avenida Paraná",
        "Avenida Pedro Taques",
        "Avenida Pingüim",
        "Avenida Pintassilgo",
        "Avenida Pioneira Devige Crepaldi Schiavoni",
        "Avenida Pioneiro Alício Arantes Campolina",
        "Avenida Pioneiro Antônio Fernandes Maciel",
        "Avenida Pioneiro Antônio Franco de Morais",
        "Avenida Pioneiro Antônio Ruiz Saldanha",
        "Avenida Pioneiro João Pereira",
        "Avenida Pioneiro Maurício Mariani",
        "Avenida Pioneiro Raul Ambrósio Valente",
        "Avenida Prefeito Sincler Sambatti",
        "Avenida Presidente Juscelino Kubitschek de Oliveira",
        "Avenida Projetada",
        "Avenida Prudente de Morais",
        "Avenida Quinze de Novembro",
        "Avenida Riachuelo",
        "Avenida Rio Branco",
        "Avenida Sabiá",
        "Avenida Senador Petrônio Portela",
        "Avenida São Domingos",
        "Avenida São Judas Tadeu",
        "Avenida São Paulo",
        "Avenida São Vicente de Paulo",
        "Avenida Tamandaré",
        "Avenida Tiradentes",
        "Avenida Torres",
        "Avenida Tuiuti",
        "Avenida Vereador Antônio Bortolotto",
        "Avenida Vereador João Batista Sanches",
        "Avenida Virgílio Manília",
        "Avenida Visconde de Taunay",
        "Avenida Vital Brasil",
        "Avenida das Grevíleas",
        "Avenida das Indústrias",
        "Avenida das Palmeiras",
        "Avenida das Torres",
        "Avenida dos Andradas",
        "Avenida dos Palmares",
        "Contorno Major Abelardo José da Cruz",
        "Estrada Acopiara",
        "Estrada Araçá",
        "Estrada Betty",
        "Estrada Carlos Borges",
        "Estrada Colombo",
        "Estrada Morangueira",
        "Estrada Oswaldo de Moraes Corrêa",
        "Estrada Progresso",
        "Estrada São José",
        "Estrada Um",
        "Estrada Valdemar",
        "Estrada Velha para Paisandú",
        "Largo Duque de Caxias",
        "Largo General Osório",
        "Largo Inocente Vila Nova Júnior",
        "Largo Júlio do Carmo Esteves",
        "Largo Pioneiro Irineu Murazi",
        "Largo Pioneiro José Ignácio da Silva",
        "Parque Alfredo Werner Nyffeler",
        "Parque do Ingá",
        "Praça 21 de Abril",
        "Praça 7 de Setembro",
        "Praça Amábile Giroldo",
        "Praça Anna Beffa Balladelli",
        "Praça Ari Barroso",
        "Praça Arnoldo Armstrong de Oliveira",
        "Praça Augusto Ruschi",
        "Praça Cidade de Bréscia",
        "Praça Deputado Heitor de Alencar Furtado",
        "Praça Deputado Renato Celidônio",
        "Praça Desembargador Franco Ferreira da Costa",
        "Praça Dona Nilza de Oliveira Pepino",
        "Praça Elídio Neto Laranjeiro",
        "Praça Emiliano Perneta",
        "Praça Emygdio de Britto",
        "Praça Emílio Fajardo Espejo",
        "Praça Farroupilha",
        "Praça Francisco Cruz Martins",
        "Praça Geoffrey Wilde Diment",
        "Praça Henrique Fregadolli",
        "Praça Jardineiro Altino Cardoso",
        "Praça Jitsuji Fujiwara",
        "Praça José Bertoni",
        "Praça José Bonifácio",
        "Praça Juiz Fernando Antônio Vieira",
        "Praça Júlio Jerônimo dos Santos",
        "Praça Leone Antônio Fregonese",
        "Praça Lions",
        "Praça Londrina",
        "Praça Luiz Gonzaga",
        "Praça Luiz M Carvalho",
        "Praça Maestro Aniceto Matti",
        "Praça Manoel Ribas",
        "Praça Megumu Tanaka",
        "Praça Ministro Antônio Oliveira Salazar",
        "Praça Monsenhor Bernardo Cnudde",
        "Praça Napoleão Moreira da Silva",
        "Praça Nossa Senhora Aparecida",
        "Praça Olinda",
        "Praça Ouro Preto",
        "Praça Pedro Álvares Cabral",
        "Praça Pio XII",
        "Praça Pioneiro Antônio Laurentino Tavares",
        "Praça Pioneiro Bento de Freitas da Silva",
        "Praça Pioneiro Fiori Progiante",
        "Praça Pioneiro Francisco Alves Toledo",
        "Praça Pioneiro Galileu Rigolin",
        "Praça Pioneiro Jacinto Ferreira Branco",
        "Praça Pioneiro Júlio Ribeiro Vilella",
        "Praça Pioneiro Olímpio Forcelli",
        "Praça Pioneiro Waldemar Pulzatto",
        "Praça Presidente Kennedy",
        "Praça Professor Ary de Lima",
        "Praça Professora Ester Gonçalves Josepetti",
        "Praça Professora Nadir Apparecida Cancian",
        "Praça Professora Rachel Dora Paraná Pintinha",
        "Praça Raphaella Name Lucchesi",
        "Praça Raposo Tavares",
        "Praça Regente Feijó",
        "Praça Reinaldo Guanaes Bittencourt Filho",
        "Praça Rocha Pombo",
        "Praça Rotary Internacional",
        "Praça Sagrado Coração de Jesus",
        "Praça Salgado Filho",
        "Praça Santa Izabel");
    private static final Random random = new Random();
    private static final List<String> nomesCompletos = new ArrayList<>(3000);
    private static final List<String> localidades = Arrays.asList("Pronto Atendimento Unimed", "Hospital Santa Rita", "Hospital Santa Casa", "Clinica própria", "Hospital São Marcos");

    public static void mains(String[] args) {
        System.out.println("Iniciando");
        for (int i = 0; i < 3000; i++) {
            nomesCompletos.add(montaNome());
        }
        for (int i = 0; i < QTD_COOPERADOS; i++) {
            String cooperado = nomesCompletos.get(random.nextInt(nomesCompletos.size()));
            //System.out.println("INSERT INTO Cooperado(ID, nome, CRM, valor_cota, adesao) values(nextVal('hibernate_sequence'), '" + cooperado + "', '" + randomCRM() + "', " + randomValorCota() + ", '" + randomAdesao() + "');");
            cooperados.add(i, cooperado);
        }
        for (String nomesCompleto : nomesCompletos) {
            //System.out.println("INSERT INTO Beneficiario(ID, nome, endereco) VALUES(nextval('hibernate_sequence'), '" + nomesCompleto + "', '" + randomEndereco() + "');");
        }
        for (String especialidade : especialidades) {
            //System.out.println("INSERT INTO Especialidade(ID, descricao) VALUES(nextval('hibernate_sequence'), '" + especialidade + "');");
        }
        for (String cooperado : cooperados) {
            int qtdEspecialidades = random.nextInt(2) + 1;
            boolean inseriuClinicaMedica = false;
            for (int i = 0; i < qtdEspecialidades; i++) {
                String especialidade = especialidades.get(random.nextInt(especialidades.size()));
                if (CLINICA_MEDICA.equals(especialidade)) {
                    inseriuClinicaMedica = true;
                }
                //System.out.println("INSERT INTO Cooperado_Especialidades(cooperados_ID, especialidades_ID) VALUES((select max(ID) from Cooperado WHERE nome = '" + cooperado + "'), (select max(ID) FROM Especialidade WHERE descricao = '" + especialidade + "'));");
            }
            if (!inseriuClinicaMedica) {
                //System.out.println("INSERT INTO Cooperado_Especialidades(cooperados_ID, especialidades_ID) VALUES((select max(ID) from Cooperado WHERE nome = '" + cooperado + "'), (select max(ID) FROM Especialidade WHERE descricao = '" + CLINICA_MEDICA + "'));");
            }
        }
        for (String procedimento : procedimentos) {
            //System.out.println("INSERT INTO Procedimento(ID, descricao, duracao, valor) VALUES(nextval('hibernate_sequence'), '" + procedimento + "', " + getRandomDuracao() + ", " + getRandomValor() + ");");
        }
        for (int i = 1; i <= 31; i++) {
            inserirConsulta("01", i);
        }
        for (int i = 1; i <= 29; i++) {
            inserirConsulta("02", i);
        }
        for (int i = 1; i <= 31; i++) {
            inserirConsulta("03", i);
        }
        for (int i = 1; i <= 30; i++) {
            inserirConsulta("04", i);
        }
        for (int i = 1; i <= 31; i++) {
            inserirConsulta("05", i);
        }
        for (int i = 1; i <= 30; i++) {
            inserirConsulta("06", i);
        }
        for (int i = 1; i <= 31; i++) {
            inserirConsulta("07", i);
        }
        for (int i =1; i <= 31; i++) {
            inserirConsulta("08", i);
        }
    }

    private static void inserirConsulta(String mes, int diaInt) {
        if (fiftyFifty()) {
            String dia = diaInt < 10 ? ("0" + diaInt) : Integer.toString(diaInt);
            for (int i = 0; i <= random.nextInt(4); i++) {
                System.out.println("INSERT INTO Consulta(id, data_consulta, localidade, criado_em, procedimento_id, cooperado_id, beneficiario_id) VALUES(nextval('hibernate_sequence'), '2016-" + mes + "-" + dia + " " + randomHora(i) + ":00.000', '" + localidades.get(random.nextInt(localidades.size())) + "', now(), (select id from procedimento order by random() limit 1), (select id from cooperado order by random() limit 1), (select id from beneficiario order by random() limit 1));");
            }
        }
    }

    private static String randomHora(int i) {
        switch (i) {
            case  0: return fiftyFifty() ? "08:00" : "08:30";
            case  1: return fiftyFifty() ? "10:30" : "11:00";
            case  2: return fiftyFifty() ? "13:30" : "14:00";
            case  3: return fiftyFifty() ? "16:00" : "16:30";
            case  4: return fiftyFifty() ? "19:00" : "19:30";
            default: return fiftyFifty() ? "22:00" : "23:00";
        }
    }

    private static String montaNome() {
        String nome = randomNome(nomes, null);
        String segundoNome = fiftyFifty() ? randomNome(nomes, nome) : "";
        String sobrenome = randomNome(sobrenomes, null);
        String segundoSobrenome = fiftyFifty() ? randomNome(sobrenomes, sobrenome) : "";
        return (nome + segundoNome + sobrenome + segundoSobrenome).trim();
    }

    private static String randomNome(List<String> source, String exclude) {
        String retorno;
        do {
            retorno = source.get(random.nextInt(source.size()));
        } while (retorno.equals(exclude));
        return retorno;
    }

    private static boolean fiftyFifty() {
        return random.nextInt(100) > 50;
    }

    private static String randomCRM() {
        return Integer.toString(random.nextInt(50000)) + "/" + randomNome(UFs, null);
    }

    private static String randomValorCota() {
        return Integer.toString((random.nextInt(20) + 1) * 25000);
    }

    private static String randomAdesao() {
        return randomAno() + "-" + randomMes().length() + "-01";
    }

    private static String randomAno() {
        return Integer.toString(1990 + random.nextInt(25));
    }

    private static String randomMes() {
        int mes = random.nextInt(12) + 1;
        return mes < 10 ? "0" + mes : Integer.toString(mes);
    }

    private static String randomEndereco() {
        return randomNome(enderecos, null) + " nº " + (random.nextInt(5000) + 1) + ", Maringá/PR";
    }

    private static String getRandomValor() {
        switch(random.nextInt(3)) {
            case 0: return "150";
            case 1: return "500";
            case 2: return "3700";
            default: return "100";
        }
    }

    private static String getRandomDuracao() {
        switch (random.nextInt(3)) {
            case 0: return "45";
            case 1: return "60";
            case 2: return "90";
            default: return "30";
        }
    }
}
