package com.gadys.config;

import com.gadys.model.Local;
import com.gadys.model.StatusLocal;
import com.gadys.model.TipoUsuario;
import com.gadys.model.Usuario;
import com.gadys.repository.LocalRepository;
import com.gadys.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LocalRepository localRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            seedUsuarios();
            seedLocais();
        } catch (Exception e) {
            System.err.println("DataInitializer falhou: " + e.getMessage());
        }
    }

    private void seedUsuarios() {
        if (!usuarioService.existeEmail("admin@gadys.com")) {
            Usuario admin = new Usuario("Administrador", "admin@gadys.com", "123456");
            admin.setTipoUsuario(TipoUsuario.ADM);
            usuarioService.salvar(admin);
        }
        if (!usuarioService.existeEmail("gadys2026@gmail.com")) {
            Usuario admin2 = new Usuario("Admin GADYS", "gadys2026@gmail.com", "123456");
            admin2.setTipoUsuario(TipoUsuario.ADM);
            usuarioService.salvar(admin2);
        }
        if (!usuarioService.existeEmail("usuario@gadys.com")) {
            usuarioService.salvar(new Usuario("Usuário Teste", "usuario@gadys.com", "123456"));
        }
    }

    private void seedLocais() {
        List<Object[]> locais = List.of(
            // { nome, descricao, categoria, subcategoria, cidade, estado, rotaFrontend }

            // AMAZONAS - Monumentos
            new Object[]{"Teatro Amazonas", "Majestoso teatro construído durante o período áureo da borracha, inaugurado em 1896. Símbolo da riqueza e cultura de Manaus.", "lugares-visitar", "monumentos", "Manaus", "Amazonas", "/teatro-amazonas"},
            new Object[]{"Forte de São José da Barra", "Fortaleza histórica construída em 1669, marco do início da colonização portuguesa em Manaus.", "lugares-visitar", "monumentos", "Manaus", "Amazonas", null},
            new Object[]{"Palácio da Justiça", "Edifício histórico com arquitetura neoclássica, construído em 1900. Hoje funciona como museu.", "lugares-visitar", "monumentos", "Manaus", "Amazonas", null},
            new Object[]{"Mercado Municipal Adolpho Lisboa", "Mercado histórico inaugurado em 1883, inspirado no mercado Les Halles de Paris. Patrimônio histórico de Manaus.", "lugares-visitar", "monumentos", "Manaus", "Amazonas", null},
            new Object[]{"Igreja de São Sebastião", "Igreja histórica do século XVIII com arquitetura colonial, localizada na Praça São Sebastião em frente ao Teatro Amazonas.", "lugares-visitar", "monumentos", "Manaus", "Amazonas", null},
            new Object[]{"Palácio Rio Negro", "Antiga residência dos governadores do Amazonas, construída no início do século XX. Hoje é centro cultural.", "lugares-visitar", "monumentos", "Manaus", "Amazonas", null},
            new Object[]{"Bumbódromo de Parintins", "Palco do maior festival folclórico do Brasil. Inaugurado em 1988, o estádio a céu aberto foi projetado em formato de cabeça de boi com capacidade para mais de 35 mil pessoas.", "lugares-visitar", "monumentos", "Parintins", "Amazonas", "/bumbodromo"},
            new Object[]{"Ponte Rio Negro", "Ponte estaiada com 3,5 km de extensão que conecta Manaus a Iranduba, cruzando o Rio Negro. Inaugurada em 2011, é um dos cartões-postais do Amazonas.", "lugares-visitar", "monumentos", "Manaus", "Amazonas", "/ponte-rio-negro"},

            // AMAZONAS - Natureza
            new Object[]{"Encontro das Águas", "Fenômeno natural onde as águas escuras do Rio Negro e as barrentas do Rio Solimões correm lado a lado por cerca de 6km sem se misturar.", "lugares-visitar", "lugares-paradisiacos", "Manaus", "Amazonas", "/encontro-aguas"},
            new Object[]{"Floresta Amazônica", "A maior floresta tropical do mundo, com mais de 5,5 milhões de km². Abriga cerca de 10% de todas as espécies do planeta.", "lugares-visitar", "lugares-paradisiacos", "Amazonas", "Amazonas", null},
            new Object[]{"Parque Nacional de Anavilhanas", "Maior arquipélago fluvial do mundo, com mais de 400 ilhas no Rio Negro. Rica biodiversidade amazônica.", "lugares-visitar", "lugares-paradisiacos", "Novo Airão", "Amazonas", null},
            new Object[]{"Arquipélago de Anavilhanas", "Conjunto de ilhas fluviais no Rio Negro, formando um dos maiores arquipélagos fluviais do mundo.", "lugares-visitar", "lugares-paradisiacos", "Novo Airão", "Amazonas", "/arquipelago-anavilhanas"},
            new Object[]{"Reserva Mamirauá", "Maior reserva de várzea do mundo com 1,1 milhão de hectares. Habitat do boto-cor-de-rosa e do peixe-boi.", "lugares-visitar", "lugares-paradisiacos", "Tefé", "Amazonas", null},
            new Object[]{"Parque Nacional do Jaú", "Uma das maiores unidades de conservação da Amazônia, com 2,3 milhões de hectares. Patrimônio Natural da Humanidade pela UNESCO.", "lugares-visitar", "lugares-paradisiacos", "Novo Airão", "Amazonas", null},
            new Object[]{"Rio Amazonas", "O maior rio do mundo em volume de água, com cerca de 6.992 km de extensão. Responsável por 20% da água doce que chega aos oceanos.", "lugares-visitar", "lugares-paradisiacos", "Amazonas", "Amazonas", null},
            new Object[]{"Cachoeira do Santuário", "Uma das mais belas quedas d'água da região amazônica, localizada em Presidente Figueiredo, a Terra das Cachoeiras, a 107 km de Manaus.", "lugares-visitar", "lugares-paradisiacos", "Presidente Figueiredo", "Amazonas", "/cachoeira-santuario"},

            // AMAZONAS - Gastronomia
            new Object[]{"Tacacá", "Prato típico amazônico servido em cuia, feito com tucupi, jambu, camarão seco e goma de tapioca. Ícone da culinária manauara.", "curiosidades", "restaurantes", "Manaus", "Amazonas", null},
            new Object[]{"Pirarucu", "Maior peixe de escamas de água doce do mundo, podendo chegar a 3 metros. Considerado o bacalhau brasileiro.", "curiosidades", "restaurantes", "Amazonas", "Amazonas", null},
            new Object[]{"Cupuaçu", "Fruto amazônico da família do cacau, usado em sucos, sorvetes, chocolates e cosméticos. Rico em vitaminas.", "curiosidades", "restaurantes", "Amazonas", "Amazonas", null},
            new Object[]{"Açaí", "Fruto do açaizeiro, rico em antioxidantes e energia. Base da alimentação amazônica, consumido com farinha ou peixe.", "curiosidades", "restaurantes", "Amazonas", "Amazonas", null},
            new Object[]{"Tucumã", "Fruto amazônico de polpa alaranjada, muito consumido em sanduíches com queijo coalho. Típico de Manaus.", "curiosidades", "restaurantes", "Amazonas", "Amazonas", null},
            new Object[]{"Farinha de Mandioca", "Ingrediente base da culinária amazônica, produzida artesanalmente pelos povos indígenas há séculos.", "curiosidades", "restaurantes", "Amazonas", "Amazonas", null},
            new Object[]{"Amazônico Peixaria", "Restaurante tradicional de Manaus especializado em peixes amazônicos como pirarucu, tambaqui e tucunaré.", "curiosidades", "restaurantes", "Manaus", "Amazonas", "/amazonico-peixaria"},
            new Object[]{"Coreto Peixaria & Café Regional", "Restaurante charmoso de Manaus que une a tradição da peixaria amazônica com o aconchego de um café regional.", "curiosidades", "restaurantes", "Manaus", "Amazonas", "/coreto-peixaria"},

            // AMAZONAS - Cultura
            new Object[]{"Festival de Parintins", "Maior festival folclórico do Brasil, realizado anualmente em junho. Disputa entre os bois Garantido e Caprichoso atrai mais de 35 mil pessoas.", "curiosidades", "costume-cultural", "Parintins", "Amazonas", null},
            new Object[]{"Lendas Amazônicas", "Rica mitologia amazônica com personagens como Curupira, Boto-cor-de-rosa, Iara, Mapinguari e Matinta Pereira.", "curiosidades", "costume-cultural", "Amazonas", "Amazonas", null},
            new Object[]{"Artesanato Indígena", "Arte tradicional dos povos indígenas amazônicos, incluindo cestaria, cerâmica, adornos de penas e esculturas em madeira.", "curiosidades", "costume-cultural", "Amazonas", "Amazonas", null},
            new Object[]{"Rituais Xamânicos", "Práticas espirituais tradicionais dos povos indígenas amazônicos, envolvendo plantas medicinais e conexão com a natureza.", "curiosidades", "costume-cultural", "Amazonas", "Amazonas", null},

            // RIO DE JANEIRO
            new Object[]{"Cristo Redentor", "Uma das Sete Maravilhas do Mundo Moderno, com 38 metros de altura no topo do Corcovado. Símbolo do Brasil no mundo.", "lugares-visitar", "monumentos", "Rio de Janeiro", "Rio de Janeiro", null},
            new Object[]{"Pão de Açúcar", "Conjunto de dois morros com teleférico, oferecendo vista panorâmica da cidade do Rio de Janeiro e da Baía de Guanabara.", "lugares-visitar", "monumentos", "Rio de Janeiro", "Rio de Janeiro", null},

            // BAHIA
            new Object[]{"Pelourinho", "Centro histórico de Salvador, Patrimônio Mundial da UNESCO. Conjunto arquitetônico colonial com igrejas barrocas e casarões coloridos.", "lugares-visitar", "monumentos", "Salvador", "Bahia", null},

            // PERNAMBUCO
            new Object[]{"Fernando de Noronha", "Arquipélago com 21 ilhas no Atlântico, Patrimônio Natural da Humanidade. Considerado um dos melhores destinos de mergulho do mundo.", "lugares-visitar", "lugares-paradisiacos", "Fernando de Noronha", "Pernambuco", null},

            // MATO GROSSO DO SUL
            new Object[]{"Pantanal", "Maior planície alagável do mundo, com 150 mil km². Maior concentração de fauna silvestre das Américas.", "lugares-visitar", "lugares-paradisiacos", "Corumbá", "Mato Grosso do Sul", null},

            // PARANÁ
            new Object[]{"Cataratas do Iguaçu", "Conjunto de 275 quedas d'água na fronteira entre Brasil e Argentina. Patrimônio Natural da Humanidade pela UNESCO.", "lugares-visitar", "lugares-paradisiacos", "Foz do Iguaçu", "Paraná", null},

            // CEARÁ
            new Object[]{"Jericoacoara", "Vila de pescadores transformada em paraíso turístico. Famosa pela duna do pôr do sol e lagoas de água doce.", "lugares-visitar", "lugares-paradisiacos", "Jijoca de Jericoacoara", "Ceará", "/ceara/jericoacoara"},
            new Object[]{"Canoa Quebrada", "Famosa praia com falésias vermelhas, dunas e lagoas. Destino boêmio com vida noturna agitada.", "lugares-visitar", "lugares-paradisiacos", "Aracati", "Ceará", "/ceara/canoa-quebrada"},
            new Object[]{"Dragão do Mar", "Centro cultural de Fortaleza com teatro, museu, planetário e espaços de arte. Coração cultural da cidade.", "lugares-visitar", "monumentos", "Fortaleza", "Ceará", "/ceara/dragao-do-mar"},
            new Object[]{"Beach Park", "Maior parque aquático da América Latina, com atrações radicais e praias privativas em Aquiraz.", "lugares-visitar", "lugares-paradisiacos", "Aquiraz", "Ceará", "/ceara/beach-park"},
            new Object[]{"Praia do Futuro", "Principal praia urbana de Fortaleza, famosa pelas barracas de frutos do mar e pela agitação aos fins de semana.", "lugares-visitar", "lugares-paradisiacos", "Fortaleza", "Ceará", "/ceara/praia-do-futuro"},
            new Object[]{"Serra de Baturité", "Maciço montanhoso com clima ameno, cachoeiras e plantações de café e banana. Refúgio ecológico do Ceará.", "lugares-visitar", "lugares-paradisiacos", "Guaramiranga", "Ceará", "/ceara/serra-de-baturite"},
            new Object[]{"Chapada do Araripe", "Planalto sedimentar com sítios paleontológicos únicos. Abriga o Geopark Araripe, primeiro da América do Sul.", "lugares-visitar", "lugares-paradisiacos", "Crato", "Ceará", "/ceara/chapada-do-araripe"},
            new Object[]{"Centro Histórico de Fortaleza", "Conjunto de edificações históricas do século XIX, incluindo a Catedral, o Theatro José de Alencar e o Mercado Central.", "lugares-visitar", "monumentos", "Fortaleza", "Ceará", "/ceara/centro-historico-fortaleza"},

            // ACRE
            new Object[]{"Parque Chandless", "Unidade de conservação com 695 mil hectares de floresta amazônica intocada na fronteira com o Peru.", "lugares-visitar", "lugares-paradisiacos", "Santa Rosa do Purus", "Acre", "/acre/parque-chandless"},
            new Object[]{"Centro Histórico de Rio Branco", "Conjunto histórico com a Ponte Metálica, o Palácio Rio Branco e o Mercado Velho às margens do Rio Acre.", "lugares-visitar", "monumentos", "Rio Branco", "Acre", "/acre/centro-historico"},

            // AMAPÁ
            new Object[]{"Fortaleza de São José de Macapá", "Maior fortaleza do Brasil colonial, construída entre 1764 e 1782. Marco histórico da ocupação portuguesa no Amapá.", "lugares-visitar", "monumentos", "Macapá", "Amapá", "/amapa/fortaleza-sao-jose"},

            // RONDÔNIA
            new Object[]{"Ferrovia Madeira-Mamoré", "Conhecida como Ferrovia do Diabo, construída entre 1907 e 1912. Hoje é patrimônio histórico com museu em Porto Velho.", "lugares-visitar", "monumentos", "Porto Velho", "Rondônia", "/rondonia/ferrovia-madeira-mamore"},

            // RORAIMA
            new Object[]{"Monte Roraima", "Tepui com 2.875 metros de altitude na fronteira entre Brasil, Venezuela e Guiana. Inspiração para o livro O Mundo Perdido de Conan Doyle.", "lugares-visitar", "lugares-paradisiacos", "Uiramutã", "Roraima", "/roraima/monte-roraima"},

            // TOCANTINS
            new Object[]{"Jalapão", "Região de cerrado com dunas, fervedouros, cachoeiras e rios de águas cristalinas. Um dos destinos ecoturísticos mais belos do Brasil.", "lugares-visitar", "lugares-paradisiacos", "Mateiros", "Tocantins", "/tocantins/jalapao"},

            // MINAS GERAIS
            new Object[]{"Ouro Preto", "Cidade histórica Patrimônio Mundial da UNESCO, com arquitetura barroca do século XVIII e museus de arte sacra.", "lugares-visitar", "monumentos", "Ouro Preto", "Minas Gerais", "/mg/ouro-preto"},
            new Object[]{"Inhotim", "Maior museu de arte contemporânea a céu aberto do mundo, com obras de artistas internacionais em jardins botânicos.", "lugares-visitar", "monumentos", "Brumadinho", "Minas Gerais", "/mg/inhotim"},

            // ESPÍRITO SANTO
            new Object[]{"Pedra Azul", "Monólito de granito com 1.822 metros de altitude no Parque Estadual da Pedra Azul. Muda de cor ao longo do dia.", "lugares-visitar", "lugares-paradisiacos", "Domingos Martins", "Espírito Santo", "/es/pedra-azul"},
            new Object[]{"Guarapari", "Cidade litorânea famosa pelas praias de areia monazítica com propriedades terapêuticas e águas cristalinas.", "lugares-visitar", "lugares-paradisiacos", "Guarapari", "Espírito Santo", "/es/guarapari"}
        );

        for (Object[] dados : locais) {
            String nome = (String) dados[0];
            String rotaFrontend = (String) dados[6];

            boolean jaExiste = rotaFrontend != null
                ? localRepository.findByRotaFrontend(rotaFrontend).isPresent()
                : localRepository.findByNomeIgnoreCase(nome).isPresent();

            if (jaExiste) continue;

            Local local = new Local();
            local.setNome(nome);
            local.setDescricao((String) dados[1]);
            local.setCategoria((String) dados[2]);
            local.setSubcategoria((String) dados[3]);
            local.setCidade((String) dados[4]);
            local.setEstado((String) dados[5]);
            local.setRotaFrontend(rotaFrontend);
            local.setStatus(StatusLocal.ATIVO);
            local.setEnviadoPor("GADYS");
            localRepository.save(local);
        }

        System.out.println("DataInitializer: locais verificados/inseridos com sucesso.");
    }
}
